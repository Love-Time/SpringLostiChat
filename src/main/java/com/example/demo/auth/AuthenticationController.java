package com.example.demo.auth;

import com.example.demo.config.JwtService;
import com.example.demo.service.BindingErrorsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

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
        System.out.println("FFFFFFFFFFFFFFFFFFFF");
        if (result.hasErrors()) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setErrors(BindingErrorsService.getErrors(result));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> updateAccessToken(@RequestBody @Valid AuthenticationRefreshRequest refreshRequest, BindingResult result) {
        if (result.hasErrors()) {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setErrors(BindingErrorsService.getErrors(result));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        String userEmail = jwtService.extractUsername(refreshRequest.getRefreshToken());
        if (userEmail != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            System.out.println(userDetails);
            if (jwtService.isTokenValid(refreshRequest.getRefreshToken(), userDetails)) {
                String accessToken = jwtService.generateToken(userDetails, 300000L);
                return new ResponseEntity<>(AuthenticationResponse.builder().accessToken(accessToken).build(), HttpStatus.OK);
            }


        }
        Map<String, String> errors = new HashMap<>();
        errors.put("refreshToken", "Token is not valid");
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().errors(errors).build();
        return new ResponseEntity<>(authenticationResponse, HttpStatus.BAD_REQUEST);
    }
}
