package com.example.demo.service;

import com.example.demo.auth.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Service
public class BindingErrorsService {
    public static Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            String fieldName = error.getField();
            errors.put(fieldName, error.getDefaultMessage());
        }
        AuthenticationResponse response = new AuthenticationResponse();
        response.setErrors(errors);
        return errors;
    }

}

