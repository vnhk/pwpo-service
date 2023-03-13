package com.pwpo.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.model.Task;
import com.pwpo.user.UserAccount;
import com.pwpo.user.model.UserProject;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Project extends BaseEntity {
    @Size(min = 1, max = Constants.SUMMARY_MAX)
    @NotNull
    @Column(length = Constants.SUMMARY_MAX)
    private String summary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;

    @Size(max = Constants.DESCRIPTION_MAX)
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;

    @Size(min = 1, max = Constants.NAME_MAX)
    @Column(unique = true, length = Constants.NAME_MAX)
    @NotNull
    private String name;

    @Size(min = 1, max = Constants.SHORT_FORM_MAX)
    @Column(unique = true, length = Constants.SHORT_FORM_MAX)
    @NotNull
    private String shortForm;

    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    @NotNull
    private UserAccount owner;

    @ManyToOne
    @JsonDeserialize(using = FromUserIdDeserializer.class)
//    @NotNull until auth
    private UserAccount createdBy;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<UserProject> addedToProjects;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<ProjectHistory> history;

    @Override
    @JsonIgnore
    public List getHistoryEntities() {
        return history;
    }
}
