package com.example.demo.service;

import com.example.demo.entity.FriendNotify;
import com.example.demo.entity.FriendStatus;
import com.example.demo.entity.User;
import com.example.demo.repository.FriendNotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendNotifyService {
    @Autowired
    private FriendNotifyRepository repository;
    @Autowired
    WsService wsService;

    public void notify(FriendStatus friendStatus, User user) {
        FriendNotify friendNotify = FriendNotify.builder()
                .user(user)
                .status(friendStatus)
                .isRead(false)
                .build();
        //Оповестить по вебсокету, что отправлен запрос в друзья
        //Добавить в уведомления
        repository.save(friendNotify);
        wsService.notifyFriend(friendNotify, user.getUsername());
    }
}
