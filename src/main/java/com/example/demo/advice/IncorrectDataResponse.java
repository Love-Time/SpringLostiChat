package com.example.demo.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class IncorrectDataResponse {
    private Map<String, String> errors;
}
