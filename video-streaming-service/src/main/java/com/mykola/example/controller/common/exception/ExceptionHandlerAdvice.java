package com.mykola.example.controller.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import com.mykola.example.controller.common.dto.CustomError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<CustomError> handleVideoIsDeleteException(VideoNotFoundException ex, ServletWebRequest request) {
        log.warn("VideoNotFoundException  exception occurred!");
        return ResponseEntity.status(404)
                .body(CustomError.builder()
                        .status(404)
                        .operation(request.getHttpMethod().name())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(StorageServiceClientException.class)
    public ResponseEntity<CustomError> handleEmptyVideoContentException(StorageServiceClientException ex, ServletWebRequest request) {
        log.warn("StorageServiceClientException exception occurred!");
        return ResponseEntity.status(500)
                .body(CustomError.builder()
                        .status(500)
                        .operation(request.getHttpMethod().name())
                        .message(ex.getMessage())
                        .build());
    }
}
