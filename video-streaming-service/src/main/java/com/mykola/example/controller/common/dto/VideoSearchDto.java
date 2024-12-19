package com.mykola.example.controller.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VideoSearchDto {
    private String title;
    private String director;
}
