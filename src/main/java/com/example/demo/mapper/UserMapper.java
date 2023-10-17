package com.example.demo.mapper;

import com.example.demo.dto.user.UserDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);

    User fromDto(UserDto dto);
    User fromDto(UserResponseDto dto);


}
