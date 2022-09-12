package com.pwpo.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.Constants;
import com.pwpo.task.Task;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String summary;
    private String status;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    private String shortForm;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails owner;
    @ManyToOne
    @JsonSerialize(using = ToFullNameSerializer.class)
    private UserDetails createdBy;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean isDeleted;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;
}
