package com.pwpo.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserDetails, Long> {
    @Query("SELECT ud FROM UserDetails as ud, UserProject as up WHERE ud.id = up.user.id AND up.project.id = ?1")
    List<UserDetails> findUsersAddedToTheProject(Long projectId);
}
