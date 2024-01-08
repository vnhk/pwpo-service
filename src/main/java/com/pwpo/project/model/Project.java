package com.pwpo.project.model;

import com.bervan.history.model.HistoryCollection;
import com.bervan.history.model.HistorySupported;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.AttachmentHandler;
import com.pwpo.common.model.Constants;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.task.model.Task;
import com.pwpo.user.UserAccount;
import com.pwpo.user.model.UserProject;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@HistorySupported
public class Project extends AttachmentHandler {
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
    @Type(type = "org.hibernate.type.TextType")
    private String descriptionHtml;

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
    private UserAccount createdBy;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserProject> addedToProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<GoalRisk> goalRisk;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @HistoryCollection(historyClass = ProjectHistory.class)
    private List<ProjectHistory> history;
}
