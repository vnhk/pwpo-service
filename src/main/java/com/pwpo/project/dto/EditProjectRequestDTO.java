package com.pwpo.project.dto;

import com.pwpo.common.enums.Status;
import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.dto.ItemDTO;
import com.pwpo.common.model.edit.Editable;
import com.pwpo.project.model.ProjectHistory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class EditProjectRequestDTO<ID extends Serializable> implements Editable<ID> {
    @NotNull
    private ID id;
    private Status status;
    private String summary;
    private String name;
    private String shortForm;
    @NotNull
    private Long owner;
    private String description;

    @Override
    public ID getEntityId() {
        return id;
    }

    @Override
    public Class<? extends ItemDTO> getResponseDTO() {
        return ProjectPrimaryResponseDTO.class;
    }

    @Override
    public Class<? extends BaseHistoryEntity> getHistoryClass() {
        return ProjectHistory.class;
    }
}
