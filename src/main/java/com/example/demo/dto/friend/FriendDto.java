package com.example.demo.dto.friend;

import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.FriendStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FriendDto {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private UserResponseDto firstUser;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private UserResponseDto userResponseDto;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    private FriendStatus status;
}
