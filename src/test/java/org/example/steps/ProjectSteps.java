package org.example.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.hamcrest.Matchers;

import static java.lang.String.format;
import static org.hamcrest.Matchers.blankOrNullString;

public class ProjectSteps {

    @Step("User checks all projects list")
    public void userChecksAllProjectsList(String projectId, String projectName) {
        var response = sendGetAllProjectsRequest();
        verifyGetAllProjectsResponse(response, projectId, projectName);
    }

    @Step("Verify project list. Project exists on the list: id '{1}', name '{2}'")
    public void verifyGetAllProjectsResponse(Response response, String projectId, String projectName) {
        response.then()
                .assertThat()
                .statusCode(200)
                .body(
                        format("find{it.id == \"%s\"}.name", projectId),
                        Matchers.equalTo(projectName));
    }

    @Step
    public Response sendGetAllProjectsRequest() {
        return SerenityRest
                .given()
                .when()
                .get("/projects");
    }

    @Step("User checks project details")
    public void userChecksProjectDetails(String projectId, String projectName) {
        var response = sendGetProjectDetails(projectId);
        verifyProjectDetailsResponse(response, projectId, projectName);
    }

    @Step("Verify project details: expected id '{1}', expected name '{2}'")
    public void verifyProjectDetailsResponse(Response response, String projectId, String projectName) {
        response .then()
                 .assertThat()
                 .statusCode(200)
                 .body("id", Matchers.equalTo(projectId))
                 .body("name", Matchers.equalTo(projectName));
    }

    @Step
    public Response sendGetProjectDetails(String projectId) {
        return SerenityRest
                .given()
                .pathParam("id", projectId)
                .log().all()
                .when()
                .get("/projects/{id}");
    }

    @Step("User creates a new project")
    public String userCreatesANewProject(String projectName) {

        var response = sendCreateANewProjectRequest(projectName);
        var projectId = verifyCreatedProjectResponse(response, projectName);
        return projectId;
    }

    @Step
    public Response sendCreateANewProjectRequest(String projectName) {

        return SerenityRest
                .given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\""+ projectName + "\"}")
                .log().all()
                .when()
                .post("/projects");
    }

    @Step("Verification of created project response. Expect name: {1}")
    public String verifyCreatedProjectResponse(Response response, String projectName) {
        return response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo(projectName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id");
    }

    @Step
    public String userCreatesANewFavoriteProject(String favoriteProjectName) {

        var response = sendCreateANewFavoriteProjectRequest(favoriteProjectName);
        var favoriteProjectId = verifyCreatedFavoriteProjectResponse(response, favoriteProjectName);
        return favoriteProjectId;
    }

    @Step
    public Response sendCreateANewFavoriteProjectRequest(String favoriteProjectName) {

        return SerenityRest
                .given()
                .contentType(ContentType.JSON)
                .body(format("{\"name\": \"%s\", \"is_favorite\": \"%s\"}",favoriteProjectName,true))
                .log().all()
                .when()
                .post("/projects");
    }

    @Step("Verification of created favorite project response. Expect name: {1}")
    public String verifyCreatedFavoriteProjectResponse(Response response, String favoriteProjectName) {

        return response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("name", Matchers.equalTo(favoriteProjectName))
                .header("Content-Type", Matchers.equalTo("application/json"))
                .and()
                .extract().path("id","is_favorite");
    }

    @Step
    public void userDeletesAProject(String projectId) {
        var response = sendDeleteAProjectRequest(projectId);
        verifyDeletedProjectResponse(response);
        }

    @Step
    public void verifyDeletedProjectResponse(Response response) {
                 response.then()
                .assertThat()
                .statusCode(204)
                .body(blankOrNullString());
    }

    @Step
    public Response sendDeleteAProjectRequest(String projectId) {
        return SerenityRest
                .given()
                .pathParam("id", projectId)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete("/projects/{id}");
    }
}

