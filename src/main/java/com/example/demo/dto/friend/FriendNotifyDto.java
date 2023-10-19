package com.example.demo.dto.friend;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.FriendStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendNotifyDto {
    private Long id;
    private UserDto user;
    private FriendStatus status;
    private Boolean isRead;
}
