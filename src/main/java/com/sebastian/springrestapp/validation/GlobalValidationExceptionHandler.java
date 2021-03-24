package com.sebastian.springrestapp.validation;

import com.sebastian.springrestapp.exception.ApiValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalValidationExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ApiValidationException.class)
    public ResponseEntity<?> handleValidationException(
            ApiValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }

}
