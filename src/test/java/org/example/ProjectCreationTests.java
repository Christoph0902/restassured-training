package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.example.steps.ProjectSteps;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class ProjectCreationTests extends BaseSetup {

    String projectName = "Lepsze szkolenie RestAssured";
    String projectToBeDeleted = "Projekt do skasowania";
    String favoriteProjectName = "Mój ulubiony projekt";

    ProjectSteps steps = new ProjectSteps();

    @Test
    public void userCanCreateANewProject() {
//        var projectName = "Lepsze szkolenie RestAssured";

        var projectId = steps.userCreatesANewProject(projectName);
        steps.userChecksProjectDetails(projectId,projectName);
        steps.userChecksAllProjectsList(projectId, projectName);

    }

    @Test
    public void userCanDeleteAProject() {
        var projectId = steps.userCreatesANewProject(projectToBeDeleted);
        steps.userChecksProjectDetails(projectId,projectToBeDeleted);
        steps.userDeletesAProject(projectId);
    }

    @Test
    public void userCanCreateAFavoriteProject() {
        var favoriteProjectId = steps.userCreatesANewFavoriteProject(favoriteProjectName);
        steps.userChecksProjectDetails(favoriteProjectId, favoriteProjectName);
        steps.userChecksAllProjectsList(favoriteProjectId, favoriteProjectName);
    }

}
