package com.example.demo.service;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.Friend;
import com.example.demo.entity.FriendStatus;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FriendService {
    @Autowired
    private FriendRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findMyFriends(Long id) {
        List<Friend> friends = repository.findByFirstUserIdOrSecondUserId(id, id);
        List<User> response = new ArrayList<>();
        for (Friend friend : friends) {
            if (Objects.equals(friend.getFirstUser().getId(), id)) {
                response.add(friend.getSecondUser());
            } else {
                response.add(friend.getFirstUser());
            }
        }
        return UserMapper.INSTANCE.toDto(response);
    }


    public Boolean isMyFriend(Long myId, Long FriendId) {
        return repository.IsMyFriend(myId, FriendId);
    }

    public FriendStatus addFriend(User user, Long friendId) throws Exception {
        Friend friend = repository.findFriend(user.getId(), friendId).orElse(null);
        if (friend == null) { //Отправка запроса в друзья
            User friendUser = userRepository.findById(friendId).orElse(null);
            if (friendUser == null) {
                throw new Exception("friend should be exists");
            }
            Friend newFriend = Friend.builder()
                    .firstUser(user)
                    .status(FriendStatus.WAIT)
                    .build();

            repository.save(newFriend);
            //Оповестить по вебсокету, что отправлен запрос в друзья
            //Добавить в уведомления
            return FriendStatus.SEND;
        } else if (friend.getStatus() == FriendStatus.WAIT) { //Принимаем в друзья
            friend.setStatus(FriendStatus.ACCEPT);
            //Оповестить по вебсокету, что мы приняли запрос в друзья
            //Добавить в уведомления
            repository.save(friend);
            return FriendStatus.ACCEPT;

        } else if (friend.getStatus() == FriendStatus.DENY) {
            if (Objects.equals(friend.getSecondUser(), user)) {
                friend.setStatus(FriendStatus.ACCEPT);
                //Мы передумали и приняли запрос в друзья
                //Добавить в уведомления
                return FriendStatus.ACCEPT;
            }
        }
        return null; //Нельзя это сделать, вам уже отказали или он ваш друг
    }

    public List<UserDto> findFriendRequests(Long id) {
        List<Friend> requests = repository.findFriendBySecondUserIdAndStatus(id, FriendStatus.WAIT);
        List<User> response = new ArrayList<>();

        for (Friend friend : requests) {
            response.add(friend.getFirstUser());
        }

        return UserMapper.INSTANCE.toDto(response);

    }

    public List<UserDto> findActiveMyRequests(Long id) {
        List<Friend> requests = repository.findFriendByFirstUserIdAndStatus(id, FriendStatus.WAIT);
        List<User> response = new ArrayList<>();

        for (Friend friend : requests) {
            response.add(friend.getSecondUser());
        }

        return UserMapper.INSTANCE.toDto(response);

    }

    public List<UserDto> findDenyRequests(Long id) {
        List<Friend> requests = repository.findFriendBySecondUserIdAndStatus(id, FriendStatus.DENY);
        List<User> response = new ArrayList<>();

        for (Friend friend : requests) {
            response.add(friend.getSecondUser());
        }

        return UserMapper.INSTANCE.toDto(response);
    }

    public List<UserDto> findDenyMyRequests(Long id) {
        List<Friend> requests = repository.findFriendByFirstUserIdAndStatus(id, FriendStatus.DENY);
        List<User> response = new ArrayList<>();

        for (Friend friend : requests) {
            response.add(friend.getSecondUser());
        }

        return UserMapper.INSTANCE.toDto(response);
    }

    public FriendStatus denyFriend(User user, Long id) {
        Friend friend = repository.findFriend(user.getId(), id).orElse(null);
        if (friend == null) {
            return null; //Некому отклонять запрос, запроса нет
        }
        if (friend.getStatus() == FriendStatus.ACCEPT)
        {
            repository.delete(friend);
            //Сообщить второму, что первый удалил его из друзей
            return FriendStatus.DELETE;

        }
        if (friend.getStatus() == FriendStatus.WAIT && Objects.equals(friend.getSecondUser().getId(), user.getId())){
            friend.setStatus(FriendStatus.DENY);
            //Сообщить второму что первый отклонил его заявку
            return FriendStatus.DENY;
        }
        return null; //Остальные случаи, ничего делать не надо

    }
}
