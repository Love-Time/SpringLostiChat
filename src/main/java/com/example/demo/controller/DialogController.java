package com.example.demo.controller;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.service.DialogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dialogs")
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @GetMapping("")
    public ResponseEntity<List<DialogDto>> getDialogs(Authentication authentication) throws SQLException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(dialogService.findMyListOfDialogs(user.getId()), HttpStatus.OK);
    }

    // Позже переделать на вебсокеты
    @PostMapping("")
    public ResponseEntity<DialogDto> addDialog(@RequestBody @Valid DialogRequestDto dto, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(dialogService.addDialogAndGetDto(dto, user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DialogDto>> getMessagesWithUser(@PathVariable Long id){
        return new ResponseEntity<>(dialogService.findMessagesWithUserById(id), HttpStatus.OK);
    }




}
