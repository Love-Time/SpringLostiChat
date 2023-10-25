package com.example.demo.service;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.Friend;
import com.example.demo.entity.FriendStatus;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.DialogRepository;
import com.example.demo.repository.FriendRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {

    @Mock
    FriendRepository friendRepository;
    @InjectMocks
    FriendService friendService;

    @Test
    public void findMyFriends(){
        Long my_id = 1L;
        User userMe = User.builder().id(1L).build();

        User userFriend2 = User.builder().id(2L).build();
        User userFriend3 = User.builder().id(3L).build();
        User userFriend4 = User.builder().id(4L).build();
        User userFriend5 = User.builder().id(5L).build();

        Friend friend1 = Friend.builder().firstUser(userMe).secondUser(userFriend2).status(FriendStatus.ACCEPT).build();
        Friend friend2 = Friend.builder().firstUser(userMe).secondUser(userFriend3).status(FriendStatus.WAIT).build();
        Friend friend3 = Friend.builder().firstUser(userFriend4).secondUser(userMe).status(FriendStatus.WAIT).build();
        Friend friend4 = Friend.builder().firstUser(userFriend5).secondUser(userMe).status(FriendStatus.ACCEPT).build();
        System.out.println(friendService);
        Mockito.when(friendRepository.findByFirstUserIdOrSecondUserId(my_id, my_id)).thenReturn(List.of(friend1, friend2, friend3, friend4));

        List<User> provided = UserMapper.INSTANCE.fromDto(friendService.findMyFriends(1L));
        List<User> expected = List.of(friend1.getSecondUser(), friend4.getFirstUser());
        Assertions.assertEquals(expected, provided);
    }

}
