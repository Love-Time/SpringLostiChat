package com.example.demo.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegisterRequestDto {
    @NotEmpty(message = "firstName should not be empty")
    private String firstname;

    @NotEmpty(message = "lastName should not be empty")
    private String lastname;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Size(min = 8, max = 30, message = "password length should be between 8 and 30")
    private String password;

}
