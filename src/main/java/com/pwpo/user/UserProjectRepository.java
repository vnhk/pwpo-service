package com.pwpo.user;

import com.pwpo.user.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    @Query("SELECT role, count(id) from UserProject WHERE id = ?1 group by role")
    Object[] getRolesChartData(Long projectId);
}
