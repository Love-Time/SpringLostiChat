package com.example.demo.controller;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.service.BindingErrorsService;
import com.example.demo.service.DialogService;
import com.example.demo.service.WsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class WebSocketController {
    @Autowired
    WsService service;
    @Autowired
    DialogService dialogService;
    @GetMapping("/hello")
    public String hello(ModelMap model) {
        return "index";
    }


    @MessageMapping("/private-messages")
    @SendToUser("/topic/private-messages")
    public void sendDialog(@RequestBody @Valid DialogRequestDto dto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        System.out.println(user);
        Dialog dialog = dialogService.addDialogAndGet(dto, user);
        service.notifyMessage(dialog);
    }







}
