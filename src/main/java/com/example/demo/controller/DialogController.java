package com.example.demo.controller;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogListPageResponse;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.service.DialogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<DialogListPageResponse> getDialogs(@RequestParam(defaultValue = "30") int size,
                                                             @RequestParam(defaultValue = "0") int page,Authentication authentication) throws SQLException {
        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(dialogService.findMyListOfDialogs(user.getId(), pageable), HttpStatus.OK);
    }

    // Позже переделать на вебсокеты
    @PostMapping("")
    public ResponseEntity<DialogDto> addDialog(@RequestBody @Valid DialogRequestDto dto, Authentication authentication) throws ObjectNotFoundException {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(dialogService.addDialogAndGetDto(dto, user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DialogListPageResponse> getMessagesWithUser(@RequestParam(defaultValue = "30") int size,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @PathVariable Long id){
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(dialogService.findMessagesWithUserById(id, pageable), HttpStatus.OK);
    }




}
