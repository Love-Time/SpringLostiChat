package com.example.demo.advice;


import com.example.demo.exception.IncorrectDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class DefaultAdvice  {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<IncorrectDataResponse> handleIncorrectDataException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getFieldErrors()) {
            String fieldName = error.getField();
            errors.put(fieldName, error.getDefaultMessage());
        }

        IncorrectDataResponse incorrectDataResponse = new IncorrectDataResponse(errors);
        return new ResponseEntity<>(incorrectDataResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response> handleAllExceptions(Exception ex) {
        Response response = new Response(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
