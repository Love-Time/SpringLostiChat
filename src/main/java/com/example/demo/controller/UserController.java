package com.example.demo.controller;

import com.example.demo.dto.user.ChangePasswordDto;
import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

import com.example.demo.service.BindingErrorsService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
//    @PostMapping
//    public ResponseEntity<UserDtoResponse> create(@RequestBody UserDTO dto) {
//        return new ResponseEntity<>(userService.create(dto), HttpStatus.OK);
//    }

    @GetMapping("")
    public ResponseEntity<List<UserDto>> readAll(){
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id, Authentication authentication){
        User user = (User)authentication.getPrincipal();
//        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public  ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(userService.update(user, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id){
        userService.delete(id);
        return HttpStatus.OK;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<UserDto> changePassword(@RequestBody @Valid  ChangePasswordDto passwordDto, BindingResult result, Authentication authentication){
        if (result.hasErrors()) {
            UserDto userDto = new UserDto();
            userDto.setErrors(BindingErrorsService.getErrors(result));
            return new ResponseEntity<>(userDto, HttpStatus.BAD_REQUEST);
        }
        User user = (User)authentication.getPrincipal();

        if (!Objects.equals(passwordEncoder.encode(passwordDto.getOldPassword()), user.getPassword())){
            UserDto userDto = new UserDto();
            Map<String, String> errors = new HashMap<>();
            errors.put("oldPassword", "oldPassword must match the current password");
            userDto.setErrors(errors);
        }


        return new ResponseEntity<>(userService.changePassword(user, passwordDto.getNewPassword()), HttpStatus.OK);
    }


}
