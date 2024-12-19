package com.mykola.example.controller.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomError {
    private int status;
    private String operation;
    private String message;
}
