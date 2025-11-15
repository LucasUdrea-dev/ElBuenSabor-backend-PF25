package com.buenSabor.BackEnd.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlerGenericException(
            MethodArgumentNotValidException exception
            , HttpServletRequest request){


        Map<String,String> apiError = new HashMap<>();

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos");

        apiError.put("message", exception.getLocalizedMessage());
        apiError.put("timestamp" ,new Date().toString());
        apiError.put("url", request.getRequestURL().toString());
        apiError.put("http-method", request.getMethod());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
