package com.example.demo.dto.dialog;

import com.example.demo.dto.user.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class DialogDto {
    private Long id;
    private UserDto sender;
    private UserDto recipient;
    private Boolean isRead;
    private Date dateTime;
    private String message;
    private DialogDto answer;
    private List<DialogDto> forwards;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;
}

