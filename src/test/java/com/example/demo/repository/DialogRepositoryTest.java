package com.example.demo.repository;


import com.example.demo.entity.Dialog;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DialogRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DialogRepository dialogRepository;

    @Test
    public void findDialogsByUserId(){
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
                .message("1")
                .sender(user1)
                .recipient(user2)
                .dateTime(new Date())
                .isRead(false)
                .build();

        Dialog dialog2 = Dialog.builder()
                .message("2")
                .sender(user2)
                .recipient(user1)
                .dateTime(new Date())
                .isRead(false)
                .build();
        Dialog dialog3 = dialogRepository.save(dialog1);
        Dialog dialog4 = dialogRepository.save(dialog2);

        List<Dialog> provided = dialogRepository.findDialogsByUserId(user1.getId());
        List<Dialog> excepted  = Arrays.asList(dialog3, dialog4);


        Assertions.assertEquals(excepted, provided);

    }
}
