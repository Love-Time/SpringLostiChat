package com.example.demo.exception;

import com.example.demo.service.BindingErrorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class IncorrectDataException extends Exception {
    private final BindingResult result;


    public IncorrectDataException(BindingResult bindingResult) {
        super("BindingResultError");
        result = bindingResult;
    }

    public Map<String, String> getErrors(){
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            String fieldName = error.getField();
            errors.put(fieldName, error.getDefaultMessage());
        }
        return errors;
    }
}
