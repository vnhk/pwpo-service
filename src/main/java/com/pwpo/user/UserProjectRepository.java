package com.pwpo.user;

import com.pwpo.common.service.BaseRepository;
import com.pwpo.project.model.Project;
import com.pwpo.user.model.UserProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserProjectRepository extends BaseRepository<UserProject, Long> {

    @Query("SELECT role, count(id) from UserProject WHERE project.id = ?1 group by role")
    Object[] getRolesChartData(Long projectId);

    Optional<UserProject> findByUserAndProject(UserAccount user, Project project);
}
