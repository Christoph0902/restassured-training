package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class ProjectCreationTests {

    String projectName = "Lepsze szkolenie RestAssured";


    @BeforeAll
    public static void setup() {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://api.todoist.com")
               .setBasePath("/rest/v2")
               .addHeader("Authorization", "Bearer 3d805d60a854c15ef18a760c629963b5030fd4a2")
//               .log(LogDetail.ALL)
                .build();
        RestAssured.requestSpecification = builder.build();

//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//        .log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // logi jeśli asercje sfailują
    }

    @Test
    public void userCanCreateANewProject() {
//        var projectName = "Lepsze szkolenie RestAssured";

        var projectId = userCreatesANewProject(projectName);
        userChecksProjectDetails(projectId,projectName);
        userChecksAllProjectsList(projectId, projectName);

    }

    @Test
    public void userCanAddTaskToTheProject() {
        var projectName = "Projekt do tworzenia zadań";
        var taskName = "Pierwsze zadanie w projekcie";
        var projectId = userCreatesANewProject(projectName);
        String taskId = userCreatesANewTask(taskName, projectId);
        userChecksIfTaskIsCreated(taskId,taskName);
        userChecksAllTasksList(taskId,taskName);

    }

    private void userChecksAllTasksList(String taskId, String taskName) {

        RestAssured
                .given()
                .log().all()
                .when()
                .get("/tasks")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body(
                        format("find{it.id == \"%s\"}.content", taskId),
                        Matchers.equalTo(taskName));
    }

    private void userChecksIfTaskIsCreated(String taskId, String taskName) {
        RestAssured
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


    private String userCreatesANewTask(String taskName, String projectId) {



        String taskId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(format("{\"content\": \"%s\", \"project_id\": \"%s\"}",taskName,projectId))
                .log().all()
                .when()
                .post("/tasks")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("content", Matchers.equalTo(taskName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id");
        return taskId;

    }

    private void userChecksAllProjectsList(String projectId, String projectName) {

        RestAssured
                .given()
                .log().all()
                .when()
                .get("/projects")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body(
                        format("find{it.id == \"%s\"}.name", projectId),
                        Matchers.equalTo(projectName));

    }

    private void userChecksProjectDetails(String projectId, String projectName) {

        RestAssured
                .given()
                .pathParam("id", projectId)
                .log().all()
                .when()
                .get("/projects/{id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(projectId))
                .body("name", Matchers.equalTo(projectName));
    }

    private String userCreatesANewProject(String projectName) {

        String projectId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+ projectName + "\"}")
                .log().all()
                .when()
                .post("/projects")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo(projectName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id");
             return projectId;
    }
}
