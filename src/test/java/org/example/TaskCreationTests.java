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
        var projectName = "Projekt do tworzenia zadań";
        var taskName = "Pierwsze zadanie w projekcie";
        var projectId = precondition.userCreatesANewProject(projectName);
        String taskId = steps.userCreatesANewTask(taskName, projectId);
        steps.userChecksIfTaskIsCreated(taskId,taskName);
        steps.userChecksAllTasksList(taskId,taskName);

    }

    @Test
    public void userCanCreateATaskOutsideProject() {
        var taskName = "Zadanie nieprzypisane do żadnego projektu";
        String taskId = steps.userCreatesANewTaskOutsideProject(taskName);
        steps.userChecksIfTaskOutsideProjectIsCreated(taskId, taskName);
    }

    @Test
    public void userCanDeleteATaskOutsideProject() {
        var taskName = "Zadanie testowe do skasowania";
        String taskId = steps.userCreatesANewTaskOutsideProject(taskName);
        steps.userChecksIfTaskOutsideProjectIsCreated(taskId, taskName);
        steps.userDeletesATaskOutsideProject(taskId);
    }

}
