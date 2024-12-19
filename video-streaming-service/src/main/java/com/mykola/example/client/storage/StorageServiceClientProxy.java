package com.mykola.example.client.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.client.storage.common.dto.ClientSaveResponseDto;

public interface StorageServiceClientProxy {
     ClientSaveResponseDto save(MultipartFile file);

     Resource getContent(String contentExternalId);
}
