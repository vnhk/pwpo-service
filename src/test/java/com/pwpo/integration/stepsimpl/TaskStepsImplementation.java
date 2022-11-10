package com.pwpo.integration.stepsimpl;

import com.pwpo.TestUtils;
import com.pwpo.common.model.APIResponse;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.dto.TaskRequestDTO;
import com.pwpo.task.model.Task;
import io.cucumber.datatable.DataTable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskStepsImplementation extends CommonStepsImplementation {

    public TaskStepsImplementation(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public void performReceiveTasksInProject(Long projectId) throws Exception {
        String params = getDefaultGetParams(Task.class);
        params += "&dto=" + TaskPrimaryResponseDTO.class.getName();

        String url = String.format("/projects/project/%d/tasks", projectId);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url + params))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();

        apiResponse = TestUtils.convertAPIResponse(result.getResponse(), TaskPrimaryResponseDTO.class, mapper);
    }

    public void performReceiveTaskById(Long taskId) throws Exception {
        String url = String.format("/tasks/task?id=%d&dto=%s", taskId, TaskPrimaryResponseDTO.class.getName());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andReturn();
    }

    public void performCreateTask(DataTable dataTable, Long projectId) throws Exception {
        TaskRequestDTO req = buildDTO(dataTable, TaskRequestDTO.class);
        req.setProject(projectId);
        mvcResult = mockMvc.perform(TestUtils.buildPostRequest("/tasks", req, mapper))
                .andReturn();
    }

    public void performReceiveCreatedTask(DataTable dataTable) throws Exception {
        APIResponse<TaskPrimaryResponseDTO> editedTaskRes
                = TestUtils.convertAPIResponse(mvcResult.getResponse(), TaskPrimaryResponseDTO.class, mapper);

        Map<String, String> data = dataTable.asMaps().get(0);
        TaskPrimaryResponseDTO edited = editedTaskRes.getItems().get(0);

        assertThat(Long.parseLong(data.get("owner"))).isEqualTo(edited.getOwner().getId());
        assertThat(Long.parseLong(data.get("assignee"))).isEqualTo(edited.getAssignee().getId());
        assertThat(data.get("status")).isEqualTo(edited.getStatus());
        assertThat(data.get("summary")).isEqualTo(edited.getSummary());
        assertThat(data.get("dueDate")).isEqualTo(edited.getDueDate().toString());
        assertThat(data.get("priority")).isEqualTo(edited.getPriority());
        assertThat(data.get("project")).isEqualTo(String.valueOf(edited.getProject().getId()));
    }
}
