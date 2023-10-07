package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void readAll(){

        User user1 = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password("12345678")
                .build();

        User user2 = User.builder()
                .id(1L)
                .firstName("fir23stname")
                .lastName("last23name")
                .password("1223345678")
                .build();


        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> provided= userService.readAll();
        List<UserDto> expected = UserMapper.INSTANCE.toDto(List.of(user1, user2));

        Assertions.assertEquals(expected, provided);

    }
    @Test
    void findById_exists(){
        User user1 = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password("12345678")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        UserDto provided = userService.findById(1L);
        UserDto expected = UserMapper.INSTANCE.toDto(user1);

        Assertions.assertEquals(expected, provided);
    }

    @Test
    void findById_notExists(){
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.findById(1L));
    }

    @Test
    void changePassword(){
        User user = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password(passwordEncoder.encode("12345678"))
                .build();

        User userWithNewPassword = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password(passwordEncoder.encode("87654321"))
                .build();

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto excepted = UserMapper.INSTANCE.toDto(userWithNewPassword);
        UserDto provided = userService.changePassword(user, "87654321");

        Assertions.assertEquals(excepted, provided);

    }
    @Test
    void update_noUpdate(){
        User user = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password(passwordEncoder.encode("12345678"))
                .build();


        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        assert user != null;
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto excepted = UserMapper.INSTANCE.toDto(user);
        UserDto provided = userService.update(1L, new UserDto());

        Assertions.assertEquals(excepted, provided);

    }

    @Test
    void update_updateFirstName(){
        User user = User.builder()
                .id(1L)
                .firstName("firstname")
                .lastName("lastname")
                .password(passwordEncoder.encode("12345678"))
                .build();

        User userExcepted = User.builder()
                .id(1L)
                .firstName("firstname2")
                .lastName("lastname")
                .password(passwordEncoder.encode("12345678"))
                .build();


        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        assert user != null;
        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDto userDto = new UserDto();
        userDto.setFirstName("firstname2");
        UserDto excepted = UserMapper.INSTANCE.toDto(userExcepted);
        UserDto provided = userService.update(1L, userDto);

        Assertions.assertEquals(excepted, provided);
    }

    @Test
    void update_UserIsNull(){
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        UserDto userDto = new UserDto();
        UserDto provided = userService.update(1L, userDto);
        Assertions.assertNull(provided);

    }





}
