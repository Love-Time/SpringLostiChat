package com.example.demo.service;


import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import com.example.demo.mapper.DialogMapper;
import com.example.demo.repository.DialogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DialogServiceTest {

    @Mock
    DialogRepository dialogRepository;
    @InjectMocks
    DialogService dialogService;

    @Test
    public void findMyListOfDialogs_easyTest(){
        User user1 = User.builder()
                .id(1L)
                .firstName("1")
                .lastName("1")
                .password("12345678")
                .email("email1@mail.ru")
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("2")
                .lastName("2")
                .password("12345678")
                .email("email2@mail.ru")
                .build();



        Dialog dialog1 = Dialog.builder()
                .id(1L)
                .message("1")
                .sender(user1)
                .recipient(user2)
                .dateTime(new Date())
                .isRead(false)
                .build();

        Dialog dialog2 = Dialog.builder()
                .id(2L)
                .message("2")
                .sender(user2)
                .recipient(user1)
                .dateTime(new Date())
                .isRead(false)
                .build();

        List<Dialog> dialogsT = new ArrayList<>();
        dialogsT.add(dialog2);
        dialogsT.add(dialog1);
        Mockito.when(dialogRepository.findDialogsByUserId(1L)).thenReturn(dialogsT);

        List<DialogDto> actual = dialogService.findMyListOfDialogs(1L);
        List<DialogDto> expected = DialogMapper.INSTANCE.toDto(List.of(dialog2));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findMyListOfDialogs_hardTest(){
        User user1 = User.builder()
                .id(1L)
                .build();

        User user2 = User.builder()
                .id(2L)
                .build();

        User user3 = User.builder()
                .id(3L)
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<Dialog> dialogs = new ArrayList<>();
        Dialog dialog;
        Long k = 0L;
        for (int i=0;i<3;i++){
            for(int j = 0; j<3; j++){
                dialog = Dialog.builder()
                        .id(k++)
                        .sender(users.get(i))
                        .recipient(users.get(j))
                        .dateTime(new Date())
                        .build();
                dialogs.add(dialog);
            }
        }
        Dialog dialog2 = Dialog.builder()
                .id(k++)
                .sender(users.get(0))
                .recipient(users.get(2))
                .dateTime(new Date())
                .build();
        dialogs.add(dialog2);

        List<Dialog> dialogsForUser1 = new ArrayList<>();

        for (int i=9; i>-1;i--) {
            if (dialogs.get(i).getRecipient().getId() == 1 || dialogs.get(i).getSender().getId() == 1) {
                dialogsForUser1.add(dialogs.get(i));
            }
        }


        Mockito.when(dialogRepository.findDialogsByUserId(1L)).thenReturn(dialogsForUser1);

        List<DialogDto> actual = dialogService.findMyListOfDialogs(1L);
        List<DialogDto> expected = DialogMapper.INSTANCE.toDto(List.of(dialogs.get(9), dialogs.get(3), dialogs.get(0)));
        Assertions.assertEquals(expected, actual);

        List<Dialog> dialogsForUser2 = new ArrayList<>();

        for (int i=9; i>-1;i--){
            if (dialogs.get(i).getRecipient().getId() == 2 || dialogs.get(i).getSender().getId() == 2){
                dialogsForUser2.add(dialogs.get(i));
            }
        }
        Mockito.when(dialogRepository.findDialogsByUserId(2L)).thenReturn(dialogsForUser2);

        List<DialogDto> actual2 = dialogService.findMyListOfDialogs(2L);
        List<DialogDto> expected2 = DialogMapper.INSTANCE.toDto(List.of(dialogs.get(7), dialogs.get(4), dialogs.get(3)));
        Assertions.assertEquals(expected2, actual2);

        List<Dialog> dialogsForUser3 = new ArrayList<>();

        for (int i=9; i>-1;i--){
            if (dialogs.get(i).getRecipient().getId() == 3 || dialogs.get(i).getSender().getId() == 3){
                dialogsForUser3.add(dialogs.get(i));
            }
        }
        Mockito.when(dialogRepository.findDialogsByUserId(3L)).thenReturn(dialogsForUser3);

        List<DialogDto> actual3 = dialogService.findMyListOfDialogs(3L);
        List<DialogDto> expected3 = DialogMapper.INSTANCE.toDto(List.of(dialogs.get(9), dialogs.get(8), dialogs.get(7)));
        Assertions.assertEquals(expected3, actual3);

    }
}
