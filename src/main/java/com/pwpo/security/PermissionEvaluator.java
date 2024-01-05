package com.pwpo.security;

import com.pwpo.common.model.APIResponse;
import com.pwpo.common.search.SearchQueryOption;
import com.pwpo.project.dto.ProjectPrimaryResponseDTO;
import com.pwpo.project.model.Project;
import com.pwpo.project.repository.ProjectRepository;
import com.pwpo.task.TaskRepository;
import com.pwpo.task.dto.TaskPrimaryResponseDTO;
import com.pwpo.task.model.Task;
import com.pwpo.user.AccountRole;
import com.pwpo.user.UserAccount;
import com.pwpo.user.UserRepository;
import com.pwpo.user.model.UserProject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public boolean writeAccessProject() {
        return activatedAndHasRole("MANAGER");
    }

    public boolean activatedAndHasRole(String role) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return activatedAndHasAnyRole() && principal.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }

    public boolean activatedAndHasAnyRole() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return isNotDisabled(principal.getAuthorities()) && principal.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_NOT_ACTIVATED"));
    }

    public boolean isNotDisabled(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_DISABLED"));
    }


    public boolean filterUsers(APIResponse apiResponse) {
        List<UserAccount> accounts = apiResponse.getItems();
        List<UserAccount> holder = new ArrayList<>();
        for (UserAccount account : accounts) {
            if (account.getRoles().contains(AccountRole.ROLE_DISABLED)) {
                holder.add(account);
            }
        }

        accounts.removeAll(holder);
        return true;
    }


    public boolean hasAccessToProject(Long projectId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserAccount> usersAddedToTheProject = userRepository.findUsersAddedToTheProject(projectId);

        return usersAddedToTheProject.stream().map(UserAccount::getNick).collect(Collectors.toList()).contains(principal.getUsername());
    }

    public boolean hasAccessToProjectTask(Long taskId) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> task = taskRepository.findById(taskId);

        return hasAccessToProjectTask(principal, task);
    }

    private boolean hasAccessToProjectTask(User principal, Optional<Task> task) {
        if (task.isPresent()) {
            List<UserProject> usersAddedToTheProject = task.get().getProject().getAddedToProjects();

            return usersAddedToTheProject.stream().map(UserProject::getUser)
                    .map(UserAccount::getNick)
                    .collect(Collectors.toList()).contains(principal.getUsername());
        }

        return false;
    }

    public boolean hasAccessToProjectTask(String taskNumber) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> task = taskRepository.findByNumber(taskNumber);

        return hasAccessToProjectTask(principal, task);
    }

    public boolean hasAccessToProject(Project project) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserProject> addedToProjects = project.getAddedToProjects();

        return addedToProjects.stream().map(UserProject::getUser).map(UserAccount::getNick)
                .collect(Collectors.toList()).contains(principal.getUsername());
    }

    public boolean filterProjects(APIResponse apiResponse) {
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

    public boolean filterSearch(APIResponse apiResponse, SearchQueryOption options) {
        String entityToFind = options.getEntityToFind().toLowerCase();
        if (entityToFind.endsWith("project")) {
            return filterProjects(apiResponse);
        } else if (entityToFind.endsWith("task")) {
            return filterTasks(apiResponse);
        } else if (entityToFind.endsWith("user")) {
            return true;
        }

        return true;
    }

    public boolean filterSearch(APIResponse apiResponse, String entityToFind) {
        if (entityToFind.equals("project")) {
            return filterProjects(apiResponse);
        } else if (entityToFind.equals("task")) {
            return filterTasks(apiResponse);
        } else if (entityToFind.equals("user")) {
            return true;
        }

        return true;
    }

    private boolean filterTasks(APIResponse apiResponse) {
        List toRemove = new ArrayList();
        List tasks = apiResponse.getItems();

        for (Object obj : tasks) {
            TaskPrimaryResponseDTO taskPrimaryResponseDTO = ((TaskPrimaryResponseDTO) obj);
            Long id = taskPrimaryResponseDTO.getId();
            if (!hasAccessToProjectTask(id)) {
                toRemove.add(obj);
            }
        }

        tasks.removeAll(toRemove);
        apiResponse.setCurrentFound(tasks.size());
        apiResponse.setAllFound(tasks.size());

        return true;
    }

    public boolean readAttachmentAccess(Long holderId) {
        boolean projectAttachment = projectRepository.findById(holderId).isPresent();
        Optional<Task> task = taskRepository.findById(holderId);
        boolean taskAttachment = task.isPresent();
        if (projectAttachment) {
            return readAccessProject(holderId);
        } else if (taskAttachment) {
            return readAccessProject(task.get().getProject().getId());
        } else {
            return false;
        }
    }

    public boolean writeAttachmentAccess(Long holderId) {
        boolean projectAttachment = projectRepository.findById(holderId).isPresent();
        Optional<Task> task = taskRepository.findById(holderId);
        boolean taskAttachment = task.isPresent();
        if (projectAttachment) {
            return writeAccessProject();
        } else if (taskAttachment) {
            return readAccessProject(task.get().getProject().getId());
        } else {
            return false;
        }
    }

    public boolean readAccessProject(Long projectId) {
        return (activatedAndHasRole("USER") && hasAccessToProject(projectId)) || writeAccessProject();
    }
}
