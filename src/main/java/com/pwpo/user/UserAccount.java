package com.pwpo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.project.model.Project;
import com.pwpo.task.model.Task;
import com.pwpo.task.timelog.TimeLog;
import com.pwpo.user.model.UserProject;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserAccount extends BaseEntity {
    @Column(unique = true)
    private String nick;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @ElementCollection(targetClass = AccountRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "account_role")
    @Column(name = "role")
    @Fetch(FetchMode.SUBSELECT)
    private Set<AccountRole> roles = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Project> ownedProjects;
    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Project> createdProjects;
    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Task> ownedTasks;
    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Task> createdTasks;
    @OneToMany(mappedBy = "assignee")
    @JsonIgnore
    private List<Task> assignedTasks;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserProject> addedToProjects;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<TimeLog> timeLogs;
    @OneToMany(mappedBy = "editor")
    @JsonIgnore
    private List<BaseHistoryEntity> edited;
}