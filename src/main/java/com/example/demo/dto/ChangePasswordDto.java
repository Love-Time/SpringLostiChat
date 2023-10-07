package com.example.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {
    @Size(min = 8, max = 30, message = "password length should be between 8 and 30")
    String oldPassword;
    @Size(min = 8, max = 30, message = "password length should be between 8 and 30")
    String newPassword;
}
