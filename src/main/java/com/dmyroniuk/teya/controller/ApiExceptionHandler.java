package com.dmyroniuk.teya.controller;

import com.dmyroniuk.teya.controller.dto.ApiErrorDto;
import com.dmyroniuk.teya.service.exception.IllegalOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ApiErrorDto> handleIllegalOperation(
            IllegalOperationException ex
    ) {
        ApiErrorDto error = new ApiErrorDto(ex.getMessage(), Instant.now());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
