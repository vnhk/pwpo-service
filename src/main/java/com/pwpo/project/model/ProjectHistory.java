package com.pwpo.project.model;

import com.bervan.history.model.HistoryField;
import com.bervan.history.model.HistoryOwnerEntity;
import com.bervan.history.model.HistorySupported;
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
@HistorySupported
public class ProjectHistory extends BaseHistoryEntity {
    @HistoryField
    private String summary;
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = ToEnumDisplayNameSerializer.class)
    @HistoryField(comparePath = "displayName")
    private Status status;
    @HistoryField
    @Column(length = Constants.DESCRIPTION_MAX)
    private String description;
    @HistoryField
    private String name;
    @HistoryField
    private String shortForm;
    @HistoryField(savePath = "owner.nick", comparePath = "nick")
    private String owner;
    @ManyToOne
    @JsonIgnore
    @HistoryOwnerEntity
    private Project project;
}