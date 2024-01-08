package com.pwpo.integration.stepsimpl;

import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.project.dto.history.ProjectHistoryDetailsResponseDTO;
import com.pwpo.project.model.Project;
import com.pwpo.project.model.ProjectHistory;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.pwpo.integration.IntegrationDataHolder.apiResponse;
import static com.pwpo.integration.IntegrationDataHolder.mvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ProjectStepsImplementation extends CommonStepsImplementation {

    public ProjectStepsImplementation(MockMvc mockMvc) {
        super(mockMvc);
    }

    public void performGetProjects() throws Exception {
        String params = super.getDefaultGetParams(Project.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        apiResponse(TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper));
    }

    public void performGetProjectById(Long projectId) throws Exception {
        String params = "?id=" + projectId + "&dto=" + ProjectPrimaryResponseDTO.class.getName();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/project" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        apiResponse(TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper));
    }

    public void performCreateProject(DataTable dataTable) throws Exception {
        ProjectRequestDTO req = buildDTO(dataTable, ProjectRequestDTO.class);

        mvcResult(mockMvc.perform(TestUtils.buildPostRequest("/projects", req, mapper)).andReturn());
    }

    public void performEditProject(DataTable dataTable, Long id) throws Exception {
        EditProjectRequestDTO req = buildDTO(dataTable, EditProjectRequestDTO.class);
        req.setId(id);

        mvcResult(mockMvc.perform(TestUtils.buildPutRequest("/projects", req, mapper)).andReturn());
    }

    public void performReceiveEditedProject(DataTable dataTable) throws Exception {
        APIResponse<ProjectPrimaryResponseDTO> editedProjectRes
                = TestUtils.convertAPIResponse(mvcResult().getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        ProjectPrimaryResponseDTO edited = editedProjectRes.getItems().get(0);

        assertThat(Long.parseLong(data.get("id"))).isEqualTo(edited.getId());
        assertThat(data.get("name")).isEqualTo(edited.getName());
        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(edited.getOwner().getId());
        assertThat(data.get("status")).isEqualTo(edited.getStatus());
        assertThat(data.get("summary")).isEqualTo(edited.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(edited.getShortForm());
    }

    public void performReceiveCreatedProject(DataTable dataTable) throws Exception {
        APIResponse<ProjectPrimaryResponseDTO> createdProjectRes
                = TestUtils.convertAPIResponse(mvcResult().getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        ProjectPrimaryResponseDTO createdProject = createdProjectRes.getItems().get(0);

        assertThat(data.get("name")).isEqualTo(createdProject.getName());
        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(createdProject.getOwner().getId());
        assertThat(data.get("status")).isEqualTo(createdProject.getStatus());
        assertThat(data.get("summary")).isEqualTo(createdProject.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(createdProject.getShortForm());
    }

    public void performGetProjectHistory(Long projectId) throws Exception {
        String params = super.getDefaultGetParams(ProjectHistory.class);

        mvcResult(mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId + "/history" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn());
    }

    public void performGetProjectHistoryDetails(Long projectId, Long historyId) throws Exception {
        mvcResult(mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId + "/history/" + historyId))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn());
    }

    public void performReceiveProjectHistoryDetails(DataTable dataTable) throws Exception {
        APIResponse<ProjectHistoryDetailsResponseDTO> detailsResp
                = TestUtils.convertAPIResponse(mvcResult().getResponse(), ProjectHistoryDetailsResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        ProjectHistoryDetailsResponseDTO details = detailsResp.getItems().get(0);

        assertThat(Long.parseLong(data.get("id"))).isEqualTo(details.getId());
        assertThat(data.get("name")).isEqualTo(details.getName());
        assertThat(data.get("owner")).isEqualTo(details.getOwner());
        assertThat(data.get("status")).isEqualTo(details.getStatus());
        assertThat(data.get("summary")).isEqualTo(details.getSummary());
        assertThat(data.get("shortForm")).isEqualTo(details.getShortForm());
        assertThat(data.get("description")).isEqualTo(details.getDescription());
        assertThat(data.get("editor")).isEqualTo(details.getEditor().getNick());
    }

    public void performCompareHistory(Long historyId, Long projectId) throws Exception {
        mvcResult(mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId + "/history/" + historyId + "/compare"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn());
    }

    public void performReceiveApiResponseWithProject(DataTable dataTable) throws Exception {
        APIResponse<ProjectPrimaryResponseDTO> detailsResp
                = TestUtils.convertAPIResponse(mvcResult().getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        List<ProjectPrimaryResponseDTO> items = detailsResp.getItems();
        assertThat(items).withFailMessage("Amount of items in dataTable is not the same as in the response!")
                .hasSize(dataTable.asMaps().size());
        List<Map<String, String>> data = dataTable.asMaps();
        for (Map<String, String> p : data) {
            String id = p.get("id");
            Optional<ProjectPrimaryResponseDTO> first = items.stream().filter(e -> e.getId().equals(Long.parseLong(id))).findFirst();
            if (first.isEmpty()) {
                fail("Project with id = " + id + " not found in API Response");
            } else {
                ProjectPrimaryResponseDTO projectPrimaryResponseDTO = first.get();
                assertThat(p.get("name")).isEqualTo(projectPrimaryResponseDTO.getName());
                assertThat(p.get("owner")).isEqualTo(projectPrimaryResponseDTO.getOwner().getId().toString());
                assertThat(p.get("status")).isEqualTo(projectPrimaryResponseDTO.getStatus());
                assertThat(p.get("summary")).isEqualTo(projectPrimaryResponseDTO.getSummary());
                assertThat(p.get("shortForm")).isEqualTo(projectPrimaryResponseDTO.getShortForm());
            }
        }

    }
}
