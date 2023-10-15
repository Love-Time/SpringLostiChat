package com.example.demo.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String accessToken;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String refreshToken;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;
}
