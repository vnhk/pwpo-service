package com.pwpo.common.model.diff;

import com.bervan.history.diff.model.DiffAttribute;
import com.pwpo.common.model.dto.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CompareResponseDTO<ID> implements ItemDTO {
    private ID entityId;
    private ID historyId;
    private List<DiffAttribute> diff;
}
