package com.example.demo.mapper;

import com.example.demo.dto.friend.FriendNotifyDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.FriendNotify;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface FriendNotifyMapper {

    FriendNotifyMapper INSTANCE = Mappers.getMapper(FriendNotifyMapper.class);

    FriendNotifyDto toDto(FriendNotify friendNotify);

}
