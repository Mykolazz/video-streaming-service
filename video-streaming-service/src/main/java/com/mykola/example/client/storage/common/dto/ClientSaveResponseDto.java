package com.mykola.example.client.storage.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientSaveResponseDto {
    private String id;
    private Double size;
}
