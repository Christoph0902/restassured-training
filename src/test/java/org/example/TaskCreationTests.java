package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.steps.ProjectSteps;
import org.example.steps.TaskSteps;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class TaskCreationTests extends BaseSetup {

    ProjectSteps precondition = new ProjectSteps();
    TaskSteps steps = new TaskSteps();

    @Test
    public void userCanAddTaskToTheProject() {
        var projectName = "Projekt do tworzenia zadań";
        var taskName = "Pierwsze zadanie w projekcie";
        var projectId = precondition.userCreatesANewProject(projectName);
        String taskId = steps.userCreatesANewTask(taskName, projectId);
        steps.userChecksIfTaskIsCreated(taskId,taskName);
        steps.userChecksAllTasksList(taskId,taskName);

    }


}
