package com.pwpo.integration.steps.task;

import com.pwpo.integration.steps.common.BaseCucumberStep;
import com.pwpo.integration.steps.common.CommonSteps;
import com.pwpo.integration.stepsimpl.TaskStepsImplementation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskSteps extends BaseCucumberStep {
    private Long projectId;
    private Long taskId;
    private TaskStepsImplementation taskStepsImplementation;
    @Autowired
    private CommonSteps commonSteps;

    @Before
    public void setUp() {
        taskStepsImplementation = new TaskStepsImplementation(mockMvc);
        commonSteps.commonStepsImplementation = taskStepsImplementation;
    }

    @Given("the client is on the project with id = {long}")
    public void theClientIsOnTheProjectWithId(Long projectId) {
        this.projectId = projectId;
    }

    @When("the client wants to receive all tasks in project")
    public void theClientWantsToReceiveAllTasksInProject() throws Exception {
        taskStepsImplementation.performReceiveTasksInProject(projectId);
    }

    @When("the client wants to receive task with id = {long}")
    public void theClientWantsToReceiveTaskById(Long taskId) throws Exception {
        taskStepsImplementation.performReceiveTaskById(taskId);
    }

    @When("the client wants to create task with following data")
    public void theClientWantsToCreateTaskWithFollowingData(DataTable dataTable) throws Exception {
        taskStepsImplementation.performCreateTask(dataTable, projectId);
    }

    @And("the client receives newly created Task")
    public void theClientReceivesNewlyCreatedTask(DataTable dataTable) throws Exception {
        taskStepsImplementation.performReceiveCreatedTask(dataTable);
    }

    @Given("the client wants to edit task with id = {long}")
    public void theClientWantsToEditTaskWithId(long taskId) {
        this.taskId = taskId;
    }

    @When("the client sets following task values")
    public void theClientSetsFollowingTaskValues(DataTable dataTable) throws Exception {
        taskStepsImplementation.performEditTask(dataTable, taskId);
    }

    @And("the client receives edited task")
    public void theClientReceivesEditedTask(DataTable dataTable) throws Exception {
        taskStepsImplementation.performReceiveEditTask(dataTable);
    }
}
