package com.rohner.springbootdemo.config;

import com.rohner.springbootdemo.controller.CustomEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(CustomEntityNotFoundException ex, WebRequest request) {
        return ResponseEntity.notFound()
                    .header("x-reason", ex.getMessage())
                    .build();
    }
}
