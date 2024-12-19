package com.mykola.example.controller.common.exception;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(Long id) {
        super(String.format("Video with id %s was not found", id));
    }
}
