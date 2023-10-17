package com.example.demo.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Map;

@Data
public class UserDto {
    @ReadOnlyProperty
    private Long id;
    private String firstName;
    private String lastName;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;
}
