package com.example.demo.service;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.friend.FriendNotifyDto;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.FriendNotify;
import com.example.demo.entity.User;
import com.example.demo.mapper.DialogMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WsService {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    UserRepository userRepository;

    public void notifyMessage(Dialog dialog) {
        System.out.println("FGGKFJGIFJGOIFJ");
        DialogDto dto = DialogMapper.INSTANCE.toDto(dialog);
        simpMessagingTemplate.convertAndSendToUser(dialog.getRecipient().getUsername(), "/topic/private-messages", dto);
    }

    public void notifyError(Object object, String username) {
        simpMessagingTemplate.convertAndSendToUser(username, "/topic/private-messages", object);
    }

    public void notifyFriend(FriendNotifyDto dto, String username){
        simpMessagingTemplate.convertAndSendToUser(username, "/topic/friends", dto);
    }
}
