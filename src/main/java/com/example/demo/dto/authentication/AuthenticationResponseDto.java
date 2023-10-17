package com.example.demo.dto.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String accessToken;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String refreshToken;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;
}
