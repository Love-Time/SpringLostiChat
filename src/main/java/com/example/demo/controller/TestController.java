package com.example.demo.controller;


import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogDtoRequest;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.entity.temp.Greeting;
import com.example.demo.entity.temp.HelloMessage;


import com.example.demo.repository.UserRepository;
import com.example.demo.service.BindingErrorsService;
import com.example.demo.service.DialogService;
import com.example.demo.service.UserService;
import com.example.demo.service.WsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class TestController {
    @Autowired
    WsService service;
    @Autowired
    DialogService dialogService;
    @GetMapping("/hello")
    public String hello(ModelMap model) {
        return "index";
    }

//    @MessageMapping("/private-message")
//    @SendToUser("/topic/private-messages")
//    public void greeting(HelloMessage message, Authentication authentication) throws Exception {
//        System.out.println(authentication + " in controller");
//        Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//        service.notifyFrontend(greeting, message.getTo());
//    }

    @MessageMapping("/private-messages")
    @SendToUser("/topic/private-messages")
    public void sendDialog(@RequestBody @Valid DialogDtoRequest dto, BindingResult result, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        if (result.hasErrors()) {
            DialogDto dialogDto = new DialogDto();
            dialogDto.setErrors(BindingErrorsService.getErrors(result));
            service.notifyError(dialogDto, user.getUsername());

        }

        Dialog dialog = dialogService.addDialogAndGet(dto, user);
        service.notifyFrontend(dialog);
    }







}
