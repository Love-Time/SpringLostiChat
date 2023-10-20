package com.example.demo.service;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.mapper.DialogMapper;
import com.example.demo.repository.DialogRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class DialogService {
    @Autowired
    private DialogRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<DialogDto> findMyListOfDialogs(Long id) {
        List<Dialog> message = repository.findDialogsByUserId(id);
        int lenDialogs = message.size();
        List<List<Long>> blackList = new ArrayList<>();
        int k = 0;

        for (int i = 0; i < lenDialogs; i++) {
            Dialog dialog = message.get(k);
            Long sender_id = dialog.getSender().getId();
            Long recipient_id = dialog.getRecipient().getId();
            List<Long> senderRecipient = Arrays.asList(sender_id, recipient_id);
            List<Long> recipientSender = Arrays.asList(recipient_id, sender_id);

            if (!(blackList.contains(senderRecipient))
                    && !(blackList.contains(recipientSender))) {
                blackList.add(senderRecipient);
                k++;
                continue;
            }
            message.remove(k);
        }


        return DialogMapper.INSTANCE.toDto(message);
    }

    public Dialog addDialogAndGet(DialogRequestDto dto, User user) {
        Dialog dialog = DialogMapper.INSTANCE.fromDto(dto);
        dialog.setSender(user);
        User recipient = userRepository.findById(dto.getRecipient_id()).orElseThrow(()-> new NoSuchElementException("Recipient not found"));
        dialog.setRecipient(recipient);
        dialog.setDateTime(new Date());
        dialog.setIsRead(false);
        System.out.println(dialog);
        return repository.save(dialog);
    }

    public DialogDto addDialogAndGetDto(DialogRequestDto dto, User user) {
        Dialog dialog = addDialogAndGet(dto, user);
        return this.toDto(dialog);
    }

    public DialogDto toDto(Dialog dialog) {
        return DialogMapper.INSTANCE.toDto(repository.save(dialog));
    }


    public List<DialogDto> findMessagesWithUserById(Long id) {
        return DialogMapper.INSTANCE.toDto(repository.findDialogsBySenderIdOrRecipientId(id, id));

    }

    public Dialog readDialog(User user, Long dialogId) {
        Dialog dialog = repository.findById(dialogId).orElseThrow(() -> new NoSuchElementException("Message not found"));
        if (Objects.equals(dialog.getRecipient().getId(), user.getId())) {
            dialog.setIsRead(true);
            repository.save(dialog);
            return dialog;

        }
        throw new SecurityException("You don't have access to this object");
    }
}
