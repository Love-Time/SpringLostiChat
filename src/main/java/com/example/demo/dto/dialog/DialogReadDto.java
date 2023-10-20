package com.example.demo.dto.dialog;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DialogReadDto {
    @NotNull(message = "dialogId should not be null")
    private Long dialogId;
}
