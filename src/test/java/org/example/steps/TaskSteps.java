package org.example.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.hamcrest.Matchers;

import static java.lang.String.format;
import static org.hamcrest.Matchers.blankOrNullString;

public class TaskSteps {

    @Step
    public String userCreatesANewTask(String taskName, String projectId) {

        String taskId = SerenityRest
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

    @Step
    public String userCreatesANewTaskOutsideProject(String taskName) {

        String taskId = SerenityRest
                .given()
                .contentType(ContentType.JSON)
                .body(format("{\"content\": \"%s\"}",taskName))
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

    @Step
    public void userChecksIfTaskIsCreated(String taskId, String taskName) {
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

        SerenityRest
                .given()
                .pathParam("id", taskId)
                .contentType(ContentType.JSON)
                .body(format("{\"content\": \"%s\"}",taskId))
                .log().all()
                .when()
                .delete("/tasks/{id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204)
                .body(blankOrNullString());
    }

    @Step
    public void userChecksAllTasksList(String taskId, String taskName) {

        SerenityRest
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
}
