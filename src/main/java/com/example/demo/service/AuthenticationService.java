package com.example.demo.service;

import com.example.demo.dto.authentication.AuthenticationRequestDto;
import com.example.demo.dto.authentication.AuthenticationResponseDto;
import com.example.demo.dto.authentication.AuthenticationRegisterRequestDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private AuthenticationResponseDto getAuthenticationResponse(User user){
        String accessToken = jwtService.generateToken(user, 300000L);
        String refreshToken = jwtService.generateToken(user, 2592000000L);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponseDto register(AuthenticationRegisterRequestDto request) {
        User user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        return this.getAuthenticationResponse(user);
    }
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        return this.getAuthenticationResponse(user);
    }
}
