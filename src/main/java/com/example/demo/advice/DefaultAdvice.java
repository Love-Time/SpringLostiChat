package com.example.demo.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class DefaultAdvice  {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Response> handleAllExceptions(Exception ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
