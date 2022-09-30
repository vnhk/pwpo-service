package com.pwpo.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.model.Constants;
import com.pwpo.common.deserializer.FromProjectIdDeserializer;
import com.pwpo.common.deserializer.FromUserIdDeserializer;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Itemable;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import com.pwpo.common.serializer.ToNickNameSerializer;
import com.pwpo.project.Project;
import com.pwpo.task.enums.TaskType;
import com.pwpo.user.UserDetails;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Itemable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    private Long id;
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constants.DB_SEQUENCE)
    @SequenceGenerator(name = Constants.DB_SEQUENCE, initialValue = Constants.DB_SEQUENCE_INIT)
    @Column(unique = true)
    private String number;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private TaskType type;
    @ManyToOne
    @JsonSerialize(using = ToNickNameSerializer.class)
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserDetails assignee;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Status status;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    private Priority priority;
    private String summary;
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    @ManyToOne
    @JsonSerialize(using = ToNickNameSerializer.class)
    @JsonDeserialize(using = FromUserIdDeserializer.class)
    private UserDetails owner;
    @ManyToOne
    @JsonSerialize(using = ToNickNameSerializer.class)
    private UserDetails createdBy;
    private String estimation;
    private boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonDeserialize(using = FromProjectIdDeserializer.class)
    private Project project;
}
