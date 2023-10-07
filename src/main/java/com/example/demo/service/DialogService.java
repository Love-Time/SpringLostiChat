package com.example.demo.service;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogDtoRequest;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.mapper.DialogMapper;
import com.example.demo.repository.DialogRepository;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DialogService {
    @Autowired
    private DialogRepository repository;

    public List<DialogDto> findMyListOfDialogs(Long id){
        List<Dialog> message = repository.findDialogsByUserId(id);
        int lenDialogs =  message.size();
        List<List<Long>> blackList = new ArrayList<>();
        int k = 0;

        for (int i = 0; i<lenDialogs; i++){
            Dialog dialog = message.get(k);
            Long sender_id = dialog.getSender().getId();
            Long recipient_id = dialog.getRecipient().getId();
            List<Long> senderRecipient = Arrays.asList(sender_id, recipient_id);
            List<Long> recipientSender = Arrays.asList(recipient_id, sender_id);

           if (!(blackList.contains(senderRecipient))
            && !(blackList.contains(recipientSender))){
                blackList.add(senderRecipient);
                k++;
                continue;
            }
           message.remove(k);
        }


        return DialogMapper.INSTANCE.toDto(message);
    }

    public DialogDto addDialog(DialogDtoRequest dto, User user) {
        Dialog dialog = DialogMapper.INSTANCE.fromDto(dto);
        dialog.setSender(user);
        User recipient = User.builder().id(dto.getRecipient_id())
        .build();
        dialog.setRecipient(recipient);
        dialog.setDateTime(new Date());
        dialog.setIsRead(false);
        System.out.println(dialog);
        return DialogMapper.INSTANCE.toDto(repository.save(dialog));
    }
}
