package com.example.demo.dto.dialog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DialogUserDto {
    private Long id;
    private String name;
}
