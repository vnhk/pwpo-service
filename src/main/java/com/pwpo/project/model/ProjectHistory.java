package com.pwpo.project.model;

import com.bervan.history.model.AbstractBaseHistoryEntity;
import com.bervan.history.model.HistoryField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pwpo.common.enums.Status;
import com.pwpo.common.model.Constants;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.serializer.ToEnumDisplayNameSerializer;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProjectHistory extends BaseHistoryEntity implements AbstractBaseHistoryEntity<Long> {
    @HistoryField
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private Status status;
    @Column(length = Constants.DESCRIPTION_MAX)
    @HistoryField
    private String description;
    @HistoryField
    private String name;
    @HistoryField
    private String shortForm;
    @HistoryField(savePath = "nick", comparePath = "nick")
    private String owner;
    @ManyToOne
    @JsonIgnore
    @HistoryField(comparable = false, isTargetEntity = true)
    private Project project;
}