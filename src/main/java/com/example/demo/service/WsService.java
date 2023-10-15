package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.entity.temp.Greeting;
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

    public void notifyFrontend(Dialog dialog) {


        simpMessagingTemplate.convertAndSendToUser(dialog.getRecipient().getUsername(), "/topic/private-messages", dialog);
    }

    public void notifyError(Object object, String username) {
        simpMessagingTemplate.convertAndSendToUser(username, "/topic/private-messages", object);
    }
}
