package com.example.demo.service;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.dto.dialog.DialogView;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.mapper.DialogMapper;
import com.example.demo.repository.DialogRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class DialogService {
    @Autowired
    private DialogRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<DialogDto> findMyListOfDialogs(Long id) throws SQLException {
        List<DialogView> message = repository.findDialogsByUserId(id);

        return DialogMapper.INSTANCE.toDtoFromView(message);
    }

    public Dialog addDialogAndGet(DialogRequestDto dto, User user) throws ObjectNotFoundException {
        Dialog dialog = DialogMapper.INSTANCE.fromDto(dto);
        dialog.setSender(user);
        User recipient = userRepository.findById(dto.getRecipient_id()).orElseThrow(()-> new ObjectNotFoundException("Recipient not found"));
        dialog.setRecipient(recipient);
        dialog.setDateTime(new Date());
        dialog.setIsRead(false);
        System.out.println(dialog);
        return repository.save(dialog);
    }

    public DialogDto addDialogAndGetDto(DialogRequestDto dto, User user) throws ObjectNotFoundException {
        Dialog dialog = addDialogAndGet(dto, user);
        return this.toDto(dialog);
    }

    public DialogDto toDto(Dialog dialog) {
        return DialogMapper.INSTANCE.toDto(repository.save(dialog));
    }


    public List<DialogDto> findMessagesWithUserById(Long id) {
        return DialogMapper.INSTANCE.toDto(repository.findDialogsBySenderIdOrRecipientId(id, id));

    }

    public Dialog readDialog(User user, Long dialogId) throws ObjectNotFoundException {
        Dialog dialog = repository.findById(dialogId).orElseThrow(() -> new ObjectNotFoundException("Message not found"));
        if (Objects.equals(dialog.getRecipient().getId(), user.getId())) {
            dialog.setIsRead(true);
            repository.save(dialog);
            return dialog;

        }
        throw new SecurityException("You don't have access to this object");
    }
}
