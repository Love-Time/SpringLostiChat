package com.example.demo.dto.friend;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FriendAddRequestDto {
    @NotNull(message = "userId should not be empty")
    private Long userId;
}
