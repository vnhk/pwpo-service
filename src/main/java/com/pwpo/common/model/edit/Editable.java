package com.pwpo.common.model.edit;

import com.pwpo.common.model.db.BaseHistoryEntity;
import com.pwpo.common.model.dto.ItemDTO;

import java.io.Serializable;

public interface Editable<ID extends Serializable> {
    ID getEntityId();

    Class<? extends ItemDTO> getResponseDTO();

    Class<? extends BaseHistoryEntity> getHistoryClass();
}
