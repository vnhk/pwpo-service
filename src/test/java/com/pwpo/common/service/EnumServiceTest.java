package com.pwpo.common.service;

import com.pwpo.common.model.EnumDTO;
import com.pwpo.common.enums.Priority;
import com.pwpo.common.model.APIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
class EnumServiceTest {
    private EnumService enumService;

    @BeforeEach
    void setUp() {
        enumService = new EnumService();
    }

    @Test
    void getEnumByNameWhenExists() {
        APIResponse priority = enumService.getEnumByName(Priority.class.getName());

        assertThat(priority.getItems()).hasSize(Priority.values().length);
        for (int i = 0; i < Priority.values().length; i++) {
            assertThat(((EnumDTO) priority.getItems().get(i)).getDisplayName()).isEqualTo(Priority.values()[i].getDisplayName());
            assertThat(((EnumDTO) priority.getItems().get(i)).getInternalName()).isEqualTo(Priority.values()[i].toString());
        }
    }

    @Test
    void getEnumByNameWhenDoesNotExist() {
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> enumService.getEnumByName("NotExistingEnum"))
                .withMessage("Could not get enum!");
    }
}