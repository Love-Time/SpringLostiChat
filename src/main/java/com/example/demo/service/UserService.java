package com.example.demo.service;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> readAll(){
        return UserMapper.INSTANCE.toDto(userRepository.findAll());
    }

    public UserDto findById(Long id){
        return UserMapper.INSTANCE.toDto(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not founded")));
    }

    public UserDto update(Long id, UserDto userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            User newUser = UserMapper.INSTANCE.fromDto(userDTO);
            user.update(newUser);
            return UserMapper.INSTANCE.toDto(userRepository.save(user));
        }

        return null;
    }


    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public UserDto changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }
}
