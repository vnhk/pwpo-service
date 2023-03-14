package com.pwpo.security;

import com.pwpo.common.model.APIResponse;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.model.Project;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.model.Task;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import com.pwpo.user.model.UserProject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public boolean hasAccessToProject(Long projectId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserAccount> usersAddedToTheProject = userRepository.findUsersAddedToTheProject(projectId);

        return usersAddedToTheProject.stream().map(UserAccount::getNick).collect(Collectors.toList()).contains(principal.getUsername());
    }

    public boolean hasAccessToProjectTask(Long taskId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            List<UserProject> usersAddedToTheProject = task.get().getProject().getAddedToProjects();

            return usersAddedToTheProject.stream().map(UserProject::getUser)
                    .map(UserAccount::getNick)
                    .collect(Collectors.toList()).contains(principal.getUsername());
        }

        return false;
    }

    public boolean hasAccessToProject(Project project) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserProject> addedToProjects = project.getAddedToProjects();

        return addedToProjects.stream().map(UserProject::getUser).map(UserAccount::getNick)
                .collect(Collectors.toList()).contains(principal.getUsername());
    }

    public boolean filter(APIResponse apiResponse) {
        List toRemove = new ArrayList();
        List projects = apiResponse.getItems();

        for (Object obj : projects) {
            ProjectPrimaryResponseDTO projectPrimaryResponseDTO = ((ProjectPrimaryResponseDTO) obj);
            Long id = projectPrimaryResponseDTO.getId();
            if (!hasAccessToProject(id)) {
                toRemove.add(obj);
            }
        }

        projects.removeAll(toRemove);
        apiResponse.setCurrentFound(projects.size());
        apiResponse.setAllFound(projects.size());

        return true;
    }

}
