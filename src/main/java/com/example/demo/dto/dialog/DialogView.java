package com.example.demo.dto.dialog;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface DialogView {
    Long getId();
    Boolean getIsRead();
    Date getDateTime();
    String getMessage();

    @Value("#{new com.example.demo.dto.dialog.DialogUserDto(target.user1Id, target.user1Name)}")
    DialogUserDto getSender();

    @Value("#{new com.example.demo.dto.dialog.DialogUserDto(target.user2Id, target.user2Name)}")
    DialogUserDto getRecipient();



}
