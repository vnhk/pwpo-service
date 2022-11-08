package com.pwpo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwpo.TestUtils;
import com.pwpo.common.enums.Status;
import com.pwpo.common.exception.ExceptionBadRequestResponse;
import com.pwpo.common.exception.ExceptionCode;
import com.pwpo.common.model.APIResponse;
import com.pwpo.common.model.diff.CompareResponseDTO;
import com.pwpo.common.search.model.SortDirection;
import com.pwpo.project.dto.EditProjectRequestDTO;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.dto.ProjectRequestDTO;
import com.pwpo.project.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = TestUtils.getObjectMapper();


    @Test
    public void getProjectsTest_whenAscSort() throws Exception {
        List<ProjectPrimaryResponseDTO> items = performGetProjects(SortDirection.ASC);
        assertThat(items.get(0).getId()).isEqualTo(1);
        assertThat(items.get(1).getId()).isEqualTo(2);
        assertThat(items.get(2).getId()).isEqualTo(3);
        assertThat(items.get(3).getId()).isEqualTo(4);
        assertThat(items.get(4).getId()).isEqualTo(5);
    }

    @Test
    public void getProjectsTest_whenDescSort() throws Exception {
        List<ProjectPrimaryResponseDTO> items = performGetProjects(SortDirection.DESC);
        assertThat(items.get(0).getId()).isEqualTo(5);
        assertThat(items.get(1).getId()).isEqualTo(4);
        assertThat(items.get(2).getId()).isEqualTo(3);
        assertThat(items.get(3).getId()).isEqualTo(2);
        assertThat(items.get(4).getId()).isEqualTo(1);
    }

    @Test
    public void getProjectPrimaryById() throws Exception {
        long projectId = 5L;

        String params = "?id=" + projectId + "&dto=" + ProjectPrimaryResponseDTO.class.getName();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/project" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        APIResponse<ProjectPrimaryResponseDTO> apiResponse =
                TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);


        assertThat(apiResponse.getAllFound()).isEqualTo(1);
        assertThat(apiResponse.getCurrentFound()).isEqualTo(1);
        assertThat(apiResponse.getCurrentPage()).isEqualTo(1);

        assertThat(apiResponse.getItems().get(0).getId()).isEqualTo(5);
    }

    private List<ProjectPrimaryResponseDTO> performGetProjects(SortDirection sort) throws Exception {
        int expectedToBeFound = 5;

        String params = TestUtils
                .buildParams(1, 1000, "id", sort, Project.class.getName(), "");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects" + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        APIResponse<ProjectPrimaryResponseDTO> apiResponse =
                TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);


        assertThat(apiResponse.getAllFound()).isEqualTo(expectedToBeFound);
        assertThat(apiResponse.getCurrentFound()).isEqualTo(expectedToBeFound);
        assertThat(apiResponse.getCurrentPage()).isEqualTo(1);


        return apiResponse.getItems();
    }

    @Test
    public void createProjectTest() throws Exception {
        ProjectRequestDTO req = new ProjectRequestDTO();
        req.setName("Create new project name");
        req.setDescription("example description");
        req.setShortForm("CTESTPR");
        req.setSummary("Create new project integration test summary");
        req.setOwner(1L);

        MvcResult result = mockMvc.perform(TestUtils.buildPostRequest("/projects", req, mapper))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        APIResponse<ProjectPrimaryResponseDTO> apiResponse =
                TestUtils.convertAPIResponse(result.getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        ProjectPrimaryResponseDTO res = apiResponse.getItems().get(0);
        assertThat(res.getName()).isEqualTo(req.getName());
        assertThat(res.getShortForm()).isEqualTo(req.getShortForm());
        assertThat(res.getStatus()).isEqualTo(Status.NEW.getDisplayName());
        assertThat(res.getSummary()).isEqualTo(req.getSummary());
        assertThat(res.getOwner().getId()).isEqualTo(req.getOwner());
    }

    @Test
    public void createProjectTest_whenProjectWithGivenNameAlreadyExist() throws Exception {
        ProjectRequestDTO req = new ProjectRequestDTO();
        req.setName("Project for tests");
        req.setDescription("example description");
        req.setShortForm("C0001");
        req.setSummary("example summary");
        req.setOwner(1L);

        MvcResult result = mockMvc.perform(TestUtils.buildPostRequest("/projects", req, mapper))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        ExceptionBadRequestResponse res = TestUtils.convertResponse(result.getResponse(), ExceptionBadRequestResponse.class, mapper);
        assertThat(res.getCode()).isEqualTo(ExceptionCode.FIELD_VALIDATION);
        assertThat(res.getMessage()).isEqualTo("Project with the given name already exists!");
        assertThat(res.getField()).isEqualTo("name");
    }

    @Test
    public void editProjectTestWithHistoryCheck() throws Exception {
        EditProjectRequestDTO<Long> req = new EditProjectRequestDTO<>();
        req.setId(5L);
        req.setName("Project for tests val");
        req.setDescription("Project that is used for testing");
        req.setShortForm("TESTED");
        req.setSummary("");
        req.setOwner(2L);
        req.setStatus(Status.CANCELED);

        MvcResult editResult = mockMvc.perform(TestUtils.buildPutRequest("/projects", req, mapper))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        APIResponse<ProjectPrimaryResponseDTO> apiResponse =
                TestUtils.convertAPIResponse(editResult.getResponse(), ProjectPrimaryResponseDTO.class, mapper);

        ProjectPrimaryResponseDTO res = apiResponse.getItems().get(0);
        assertThat(res.getId()).isEqualTo(req.getId());
        assertThat(res.getName()).isEqualTo(req.getName());
        assertThat(res.getShortForm()).isEqualTo(req.getShortForm());
        assertThat(res.getStatus()).isEqualTo(req.getStatus().getDisplayName());
        assertThat(res.getSummary()).isEqualTo(req.getSummary());
        assertThat(res.getOwner().getId()).isEqualTo(req.getOwner());

        String wholeHistoryParams = TestUtils
                .buildParams(1, 1000, "id", SortDirection.ASC, null, "");

        MvcResult historyResult = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + res.getId() + "/history" + wholeHistoryParams))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        Map map = mapper.readValue(historyResult.getResponse().getContentAsString(), Map.class);
        ArrayList arrayList = (ArrayList) map.get("items");
        LinkedHashMap item = (LinkedHashMap) arrayList.get(0);
        long historyId = Long.parseLong(String.valueOf(item.get("id")));

        MvcResult compareProjectResult = mockMvc.perform(MockMvcRequestBuilders.get(
                "/projects/" + res.getId() + "/history/" + historyId + "/compare"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        APIResponse<CompareResponseDTO> compareProjectRes =
                TestUtils.convertAPIResponse(compareProjectResult.getResponse(), CompareResponseDTO.class, mapper);


        CompareResponseDTO compareResponseDTO = compareProjectRes.getItems().get(0);
        assertThat(Long.parseLong(String.valueOf(compareResponseDTO.getHistoryId()))).isEqualTo(Long.parseLong(String.valueOf(historyId)));
        assertThat(Long.parseLong(String.valueOf(compareResponseDTO.getEntityId()))).isEqualTo(Long.parseLong(String.valueOf(res.getId())));
    }
}
