package com.pwpo.integration.project;

import com.pwpo.TestUtils;
import com.pwpo.common.exception.ExceptionBadRequestResponse;
import com.pwpo.common.model.APIResponse;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.project.model.Project;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectSteps extends ProjectCucumberOperations {
    protected APIResponse apiResponse;
    protected MvcResult mvcResult;

    @When("the client wants to receive all the projects")
    public void theClientWantsToReceiveAllProjects() throws Exception {
        String defaultGetParams = getDefaultGetParams(Project.class);
        apiResponse = performGetProjects(defaultGetParams);
    }

    @Then("the client receives APIResponse")
    public void clientReceivesAPIResponse(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(Integer.parseInt(data.get("allFound")))
                .isEqualTo(apiResponse.getAllFound());

        assertThat(Integer.parseInt(data.get("currentPage")))
                .isEqualTo(apiResponse.getCurrentPage());

        assertThat(Integer.parseInt(data.get("currentFound")))
                .isEqualTo(apiResponse.getCurrentFound());
    }

    @When("^the client wants to receive project by id = (\\d+)$")
    public void theClientWantsToReceiveProjectById(Long id) throws Exception {
        apiResponse = getProjectById(id);
    }

    @When("the client wants to create project with following data")
    public void theClientWantsToReceiveProjectById(DataTable dataTable) throws Exception {
        ProjectRequestDTO dto = buildDTO(dataTable, ProjectRequestDTO.class);
        mvcResult = createProject(dto);
    }

    @And("the client receives newly created Project")
    public void theClientReceivesNewlyCreatedProject(DataTable dataTable) {
        ProjectPrimaryResponseDTO createdProject = (ProjectPrimaryResponseDTO) apiResponse.getItems().get(0);
        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(data.get("name")).isEqualTo(createdProject.getName());
        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(createdProject.getOwner().getId());
        assertThat(data.get("status")).isEqualTo(createdProject.getStatus());
        assertThat(data.get("summary")).isEqualTo(createdProject.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(createdProject.getShortForm());
    }

    @Then("the client receives {int} status")
    public void theClientReceivesStatus(int status) {
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(status);
    }

    @And("the client receives validation details")
    public void theClientReceivesValidationDetails(DataTable dataTable) throws Exception {
        ExceptionBadRequestResponse res = TestUtils.convertResponse(mvcResult.getResponse(), ExceptionBadRequestResponse.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);

        assertThat(data.get("field")).isEqualTo(res.getField());
        assertThat(data.get("code")).isEqualTo(res.getCode().name());
        assertThat(data.get("message")).isEqualTo(res.getMessage());
    }
}
