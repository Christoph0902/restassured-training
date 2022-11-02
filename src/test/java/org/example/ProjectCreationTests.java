package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class ProjectCreationTests {

    @Test
    public void userCanCreateANewProject() {
        RestAssured
                .given()
                .baseUri("https://api.todoist.com")
                .basePath("/rest/v2")
                .header("Authorization", "Bearer 3d805d60a854c15ef18a760c629963b5030fd4a2")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Szkolenie Rest API\"}")
                .log().all()
                .when()
                .post("/projects")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo("Szkolenie Rest API"))
                .header("Content-Type", Matchers.equalTo("application/json"));

        RestAssured
                .given()
                .baseUri("https://api.todoist.com")
                .basePath("/rest/v2")
                .header("Authorization", "Bearer 3d805d60a854c15ef18a760c629963b5030fd4a2")
                .log().all()
                .when()
                .get("/projects/2301674932")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo("2301674932"))
                .body("name", Matchers.equalTo("Szkolenie Rest API"));
    }
}
