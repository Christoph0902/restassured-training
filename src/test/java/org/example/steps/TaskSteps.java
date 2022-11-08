package org.example.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.example.model.NewProjectPayload;
import org.example.model.NewTaskPayload;
import org.hamcrest.Matchers;

import static java.lang.String.format;
import static org.hamcrest.Matchers.blankOrNullString;

public class TaskSteps {

    @Step("User creates a new task in a project")
    public String userCreatesANewTask(String taskName, String projectId) {
        var response = sendCreateANewTaskRequest(taskName, projectId);
        var taskId = verifyCreatedTaskResponse(response, taskName);
        return taskId;
    }

    @Step
    public Response sendCreateANewTaskRequest(String taskName, String projectId) {

        NewTaskPayload payload = new NewTaskPayload(taskName,projectId);
        return SerenityRest
                .given()
                .contentType(ContentType.JSON)
//                .body(format("{\"content\": \"%s\", \"project_id\": \"%s\"}",taskName,projectId))
                .body(payload)
                .when()
                .post("/tasks");
    }

    @Step("Verification of created task response. Expect name: {1}")
    public String verifyCreatedTaskResponse(Response response, String taskName) {
        return response.then()
                .assertThat()
                .statusCode(200)
                .body("content", Matchers.equalTo(taskName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id");
    }

    @Step("User creates a new task outside project")
    public String userCreatesANewTaskOutsideProject(String taskName) {
        var response = sendCreateANewTaskOutsideProjectRequest(taskName);
        var taskId = verifyCreatedTaskOutsideProjectResponse(response, taskName);
        return taskId;
    }

    @Step
    public Response sendCreateANewTaskOutsideProjectRequest(String taskName) {
        NewTaskPayload payload = new NewTaskPayload(taskName);
        return SerenityRest
                .given()
                .contentType(ContentType.JSON)
//                .body(format("{\"content\": \"%s\"}",taskName))
                .body(payload)
                .when()
                .post("/tasks");
    }

    @Step("Verification of created task outside project response. Expect name: {1}")
    public String verifyCreatedTaskOutsideProjectResponse(Response response, String taskName) {
        return response.then()
                .assertThat()
                .statusCode(200)
                .body("content", Matchers.equalTo(taskName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id");
    }

    @Step("User checks task details")
    public void userChecksIfTaskIsCreated(String taskId, String taskName) {
        var response = sendGetTaskDetailsRequest(taskId);
        verifyTaskDetailsResponse(response, taskId, taskName);
    }

    @Step
    public Response sendGetTaskDetailsRequest(String taskId) {
        return SerenityRest
                .given()
                .pathParam("id", taskId)
                .when()
                .get("/tasks/{id}");
    }

    @Step("Verify task details: expected id '{1}', expected name '{2}'")
    public void verifyTaskDetailsResponse(Response response, String taskId, String taskName) {
        response.then()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(taskId))
                .body("content", Matchers.equalTo(taskName));
    }

    @Step
    public void userChecksIfTaskOutsideProjectIsCreated(String taskId, String taskName) {

        SerenityRest
                .given()
                .pathParam("id", taskId)
                .log().all()
                .when()
                .get("/tasks/{id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(taskId))
                .body("content", Matchers.equalTo(taskName));
    }

    @Step
    public void userDeletesATaskOutsideProject(String taskId) {
        var response = sendDeleteATaskOutsideProjectRequest(taskId);
        verifyDeletedTaskOutsideProjectResponse(response);
    }

    @Step
    public Response sendDeleteATaskOutsideProjectRequest(String taskId) {
        return SerenityRest
                .given()
                .pathParam("id", taskId)
                .contentType(ContentType.JSON)
                .body(format("{\"content\": \"%s\"}",taskId))
                .when()
                .delete("/tasks/{id}");
    }

    @Step
    public void verifyDeletedTaskOutsideProjectResponse(Response response) {
        response.then()
                .assertThat()
                .statusCode(204)
                .body(blankOrNullString());
    }

    @Step("User checks all tasks list")
    public void userChecksAllTasksList(String taskId, String taskName) {
        var response = sendGetAllTasksRequest();
        verifyGetAllTasksResponse(response, taskId, taskName);
    }

    @Step("Verify tasks list. Task exists on the list: id '{1}', name '{2}'")
    private void verifyGetAllTasksResponse(Response response, String taskId, String taskName) {
        response.then()
                .assertThat()
                .statusCode(200)
                .body(
                        format("find{it.id == \"%s\"}.content", taskId),
                        Matchers.equalTo(taskName));
    }

    @Step
    public Response sendGetAllTasksRequest() {
        return SerenityRest
                .given()
                .when()
                .get("/tasks");
    }
}
