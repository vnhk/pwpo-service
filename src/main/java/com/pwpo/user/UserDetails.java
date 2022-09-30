package com.pwpo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwpo.common.model.Itemable;
import com.pwpo.common.model.UserProject;
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
public class UserDetails implements Itemable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nick", unique = true)
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
}
