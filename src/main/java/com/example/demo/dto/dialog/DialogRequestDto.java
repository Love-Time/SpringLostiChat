package com.example.demo.dto.dialog;

import com.example.demo.dto.dialog.DialogDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DialogRequestDto {
    @NotNull(message = "recipient should not be empty")
    private Long recipient_id;
    private String message;
    private DialogDto answer;
    private List<DialogDto> forwards;

    @NotNull(message = "if message is empty, forwards not should be empty")
    public Object validate(){
        if (message!=null){
            return message;
        }
        else return forwards;
    }
}
