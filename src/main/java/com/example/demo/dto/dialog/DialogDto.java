package com.example.demo.dto.dialog;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DialogDto implements DialogView {
    private Long id;
    private DialogUserDto sender;
    private DialogUserDto recipient;
    private Boolean isRead;
    private Date dateTime;
    private String message;


}

