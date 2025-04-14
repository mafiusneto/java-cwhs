package com.oten.test.infra.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(HttpClientErrorException.Unauthorized ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Token inválido ou expirado.");
        response.put("status", "401");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientErrors(HttpClientErrorException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Erro na requisição: " + ex.getStatusCode());
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }   
}
