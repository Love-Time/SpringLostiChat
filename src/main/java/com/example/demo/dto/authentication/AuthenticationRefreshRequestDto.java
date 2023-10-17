package com.example.demo.dto.authentication;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRefreshRequestDto {
    @NotEmpty(message = "refreshToken should not be empty")
    private String refreshToken;


}
