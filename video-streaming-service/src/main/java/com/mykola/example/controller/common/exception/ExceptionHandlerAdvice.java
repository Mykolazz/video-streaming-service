package com.mykola.example.controller.common.exception;

import org.springframework.http.HttpStatus;
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
    public CustomError handleVideoIsDeleteException(VideoNotFoundException ex, ServletWebRequest request) {
        log.warn("VideoNotFoundException  exception occurred!");
        return CustomError.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .operation(request.getHttpMethod().name())
                        .message(ex.getMessage())
                        .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(StorageServiceClientException.class)
    public CustomError handleEmptyVideoContentException(StorageServiceClientException ex, ServletWebRequest request) {
        log.warn("StorageServiceClientException exception occurred!");
        return CustomError.builder()
                        .status(500)
                        .operation(request.getHttpMethod().name())
                        .message(ex.getMessage())
                        .build();
    }
}
