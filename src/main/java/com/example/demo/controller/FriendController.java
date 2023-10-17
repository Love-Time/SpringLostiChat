package com.example.demo.controller;


import com.example.demo.dto.friend.FriendAddRequestDto;
import com.example.demo.dto.friend.FriendDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.FriendAccept;
import com.example.demo.entity.User;
import com.example.demo.service.BindingErrorsService;
import com.example.demo.service.FriendService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/friends")
public class FriendController {
    @Autowired
    private FriendService service;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> getFriends(Authentication authentication){
        User user = (User)authentication.getPrincipal();
        return new ResponseEntity<>(service.findMyFriends(user.getId()), HttpStatus.OK);
    }
    @GetMapping("/{id}/isFriend")
    public ResponseEntity<Boolean> IsHeMyFriend(@PathVariable Long id, Authentication authentication){
        User user = (User)authentication.getPrincipal();
        return new ResponseEntity<>(service.isMyFriend(user.getId(), id), HttpStatus.OK);
    }

    @PostMapping("")
    public  ResponseEntity<FriendDto> addFriend(@RequestBody @Valid FriendAddRequestDto friendAddRequestDto, BindingResult result, Authentication authentication) throws Exception {
        if (result.hasErrors()) {
            FriendDto friendDto = FriendDto.builder()
                    .errors(BindingErrorsService.getErrors(result))
                    .build();

            return new ResponseEntity<>(friendDto, HttpStatus.BAD_REQUEST);
        }

        User user = (User)authentication.getPrincipal();
        FriendAccept status = service.addFriend(user,friendAddRequestDto.getUserId());
        if (status == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("status", "The friend request already exists. Either you are already friends, or he rejected your friend request");
            FriendDto friendDto = FriendDto.builder()
                    .errors(errors)
                    .build();
            return new ResponseEntity<>(friendDto, HttpStatus.BAD_REQUEST);

        }
        FriendDto friendDto = FriendDto.builder()
                .status(status)
                .build();

        return new ResponseEntity<>(friendDto, HttpStatus.OK);
    }



}
