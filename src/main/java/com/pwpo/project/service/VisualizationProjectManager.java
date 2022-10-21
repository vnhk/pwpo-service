package com.pwpo.project.service;

import com.pwpo.common.ChartData;
import com.pwpo.common.enums.Priority;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.enums.TaskType;
import com.pwpo.user.ProjectRole;
import com.pwpo.user.UserProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisualizationProjectManager {
    private final TaskRepository taskRepository;
    private final UserProjectRepository userProjectRepository;

    public ChartData[] getRolesChartData(Long projectId) {
        Object[] projectRoleChartData = userProjectRepository.getRolesChartData(projectId);

        List<ChartData> chartData = new ArrayList<>();

        List<String> projectRoles = Arrays.stream(ProjectRole.values()).map(ProjectRole::getDisplayName).collect(Collectors.toList());

        for (Object val : projectRoleChartData) {
            Object[] taskTypeChart = (Object[]) val;
            ProjectRole role = (ProjectRole) taskTypeChart[0];
            Long value = (Long) taskTypeChart[1];
            chartData.add(new ChartData(role.getDisplayName(), value));
            projectRoles.remove(role.getDisplayName());
        }

        for (String role : projectRoles) {
            chartData.add(new ChartData(role, 0));
        }

        return chartData.toArray(ChartData[]::new);
    }

    public ChartData[] getTaskTypes(Long projectId) {
        Object[] taskTypeChartData = taskRepository.getTaskTypeChartData(projectId);

        List<ChartData> chartData = new ArrayList<>();

        List<String> taskTypes = Arrays.stream(TaskType.values()).map(TaskType::getDisplayName).collect(Collectors.toList());

        for (Object val : taskTypeChartData) {
            Object[] taskTypeChart = (Object[]) val;
            TaskType type = (TaskType) taskTypeChart[0];
            Long value = (Long) taskTypeChart[1];
            chartData.add(new ChartData(type.getDisplayName(), value));
            taskTypes.remove(type.getDisplayName());
        }

        for (String taskType : taskTypes) {
            chartData.add(new ChartData(taskType, 0));
        }

        return chartData.toArray(ChartData[]::new);
    }

    public ChartData[] getTaskPriorities(Long projectId) {
        Object[] taskPrioritiesChartData = taskRepository.getTaskPriorityChartData(projectId);

        List<ChartData> chartData = new ArrayList<>();

        List<String> priorities = Arrays.stream(Priority.values()).map(Priority::getDisplayName).collect(Collectors.toList());

        for (Object val : taskPrioritiesChartData) {
            Object[] taskPriority = (Object[]) val;
            Priority priority = (Priority) taskPriority[0];
            Long value = (Long) taskPriority[1];
            chartData.add(new ChartData(priority.getDisplayName(), value));
            priorities.remove(priority.getDisplayName());
        }

        for (String priority : priorities) {
            chartData.add(new ChartData(priority, 0));
        }

        return chartData.toArray(ChartData[]::new);
    }

    public ChartData[] getProjectSumTimeChartData(Long projectId) {
        Long estimationData = taskRepository.getProjectSumEstimationTimeChartData(projectId);
        Long loggedData = taskRepository.getProjectSumLoggedTimeChartData(projectId);

        ChartData[] chartData = new ChartData[2];
        chartData[0] = new ChartData("Estimation", estimationData == null ? 0 : estimationData);
        chartData[1] = new ChartData("Logged", loggedData == null ? 0 : loggedData);

        return chartData;
    }
}
