package com.pwpo.common.model.diff;

import com.pwpo.common.model.dto.ItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CompareResponseDTO implements ItemDTO {
    private Long entityId;
    private Long historyId;
    private List<DiffAttribute> diff;
}
