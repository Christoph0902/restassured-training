package org.example.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

import static java.lang.String.format;

public class TaskSteps {

    public String userCreatesANewTask(String taskName, String projectId) {



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

    public void userChecksIfTaskIsCreated(String taskId, String taskName) {
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

    public void userChecksAllTasksList(String taskId, String taskName) {

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
}
