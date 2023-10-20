package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BindingErrorsService {
    public static Map<String, String> getErrors(List<FieldError> fields) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : fields) {
            String fieldName = error.getField();
            errors.put(fieldName, error.getDefaultMessage());
        }
        return errors;
    }

}

