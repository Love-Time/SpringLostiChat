package com.example.demo.auth;

import com.example.demo.service.BindingErrorsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request, BindingResult result
    ) {
        if (result.hasErrors()) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setErrors(BindingErrorsService.getErrors(result));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody @Valid AuthenticationRequest request, BindingResult result
    ) {
        if (result.hasErrors()) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setErrors(BindingErrorsService.getErrors(result));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.ok(service.authenticate(request));
    }


}
