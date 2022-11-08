package org.example;

import net.thucydides.core.annotations.Steps;
import org.example.steps.ProjectSteps;
import org.example.steps.TaskSteps;
import org.junit.jupiter.api.Test;

public class TaskCreationTests extends BaseSetup {

    @Steps
    ProjectSteps precondition;
    @Steps
    TaskSteps steps;

    @Test
    public void userCanAddTaskToTheProject() {
        var projectName = generator.book().genre();
        var taskName =  generator.book().title();
        var projectId = precondition.userCreatesANewProject(projectName);
        String taskId = steps.userCreatesANewTask(taskName, projectId);
        steps.userChecksIfTaskIsCreated(taskId,taskName);
        steps.userChecksAllTasksList(taskId,taskName);

    }

    @Test
    public void userCanCreateATaskOutsideProject() {
        var taskName = generator.lordOfTheRings().location();
        String taskId = steps.userCreatesANewTaskOutsideProject(taskName);
        steps.userChecksIfTaskOutsideProjectIsCreated(taskId, taskName);
    }

    @Test
    public void userCanDeleteATaskOutsideProject() {
        var taskName = generator.ancient().god();
        String taskId = steps.userCreatesANewTaskOutsideProject(taskName);
        steps.userChecksIfTaskOutsideProjectIsCreated(taskId, taskName);
        steps.userDeletesATaskOutsideProject(taskId);
    }

}
