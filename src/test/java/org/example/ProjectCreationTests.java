package org.example;

import net.thucydides.core.annotations.Steps;
import org.example.steps.ProjectSteps;
import org.junit.jupiter.api.Test;

public class ProjectCreationTests extends BaseSetup {

    @Steps
    ProjectSteps steps;

    @Test
    public void userCanCreateANewProject() {
        var projectName = generator.lebowski().character();
        var projectId = steps.userCreatesANewProject(projectName);
        steps.userChecksProjectDetails(projectId,projectName);
        steps.userChecksAllProjectsList(projectId, projectName);
    }

    @Test
    public void userCanDeleteAProject() {
        var projectToBeDeleted = generator.artist().name();
        var projectId = steps.userCreatesANewProject(projectToBeDeleted);
        steps.userChecksProjectDetails(projectId,projectToBeDeleted);
        steps.userDeletesAProject(projectId);
    }

    @Test
    public void userCanCreateAFavoriteProject() {
        String favoriteProjectName = generator.funnyName().name();
        var favoriteProjectId = steps.userCreatesANewFavoriteProject(favoriteProjectName);
        steps.userChecksProjectDetails(favoriteProjectId, favoriteProjectName);
        steps.userChecksAllProjectsList(favoriteProjectId, favoriteProjectName);
    }

}
