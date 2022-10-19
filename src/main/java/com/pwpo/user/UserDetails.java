package com.pwpo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwpo.task.timelog.TimeLog;
import com.pwpo.user.model.BaseEntity;
import com.pwpo.user.model.UserProject;
import com.pwpo.project.Project;
import com.pwpo.task.Task;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nick;
    private String fullName;
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
}
