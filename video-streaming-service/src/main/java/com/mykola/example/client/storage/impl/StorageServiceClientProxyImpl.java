package com.mykola.example.client.storage.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.client.storage.StorageServiceClientProxy;
import com.mykola.example.client.storage.common.dto.ClientSaveResponseDto;
import com.mykola.example.controller.common.exception.StorageServiceClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageServiceClientProxyImpl implements StorageServiceClientProxy {
    private static final String SAVE_CONTENT_URL_KEY = "save-content";
    private static final String GET_CONTENT_URL_KEY = "get-content";

    @Override
    public ClientSaveResponseDto save(MultipartFile file) {
        var random = new Random();
        var randDouble = random.nextDouble();

        try {
            return ClientSaveResponseDto.builder()
                    .id(UUID.randomUUID().toString())
                    .size(randDouble)
                    .build();
        } catch (Exception e) {
            log.error("CustomError while saving content: " + e.getMessage());
            throw new StorageServiceClientException("CustomError while saving content: " + e.getMessage());
        }

    }

    @Override
    public Resource getContent(String contentExternalId) {
        try {
            return generateMockMp4Resource();
        } catch (Exception e) {
            log.error("CustomError while getting content: " + e.getMessage());
            throw new StorageServiceClientException("CustomError while getting content: " + e.getMessage());
        }
    }

    private Resource generateMockMp4Resource() throws IOException {
        // Step 1: Create a temporary file
        var tempPath = Files.createTempFile("temp-video", ".mp4");

        // Step 2: Optionally write dummy data - here we just write the string 'dummy video data'
        byte[] buffer = new byte[1024];
        Files.write(tempPath, buffer);

        // Step 3: Load file as Resource
        File file = tempPath.toFile();
        return new FileSystemResource(file);
    }

}
