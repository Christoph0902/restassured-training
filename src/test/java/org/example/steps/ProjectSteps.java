package org.example.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;

import static java.lang.String.format;
import static org.hamcrest.Matchers.blankOrNullString;

public class ProjectSteps {

    public void userChecksAllProjectsList(String projectId, String projectName) {

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

    public void userChecksProjectDetails(String projectId, String projectName) {

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

    public String userCreatesANewProject(String projectName) {

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

    public String userCreatesANewFavoriteProject(String favoriteProjectName) {

        String favoriteProjectId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(format("{\"name\": \"%s\", \"is_favorite\": \"%s\"}",favoriteProjectName,true))
                .log().all()
                .when()
                .post("/projects")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo(favoriteProjectName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id","is_favorite");
        return favoriteProjectId;
    }

    public void userDeletesAProject(String projectId) {

            RestAssured
                    .given()
                    .pathParam("id", projectId)
                    .contentType(ContentType.JSON)
                    .log().all()
                    .when()
                    .delete("/projects/{id}")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(204)
                    .body(blankOrNullString());
        }
    }

