package com.mykola.example.controller.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateMetadataDto {
    private String title;
    private String synopsis;
    private String director;
    private String actors;
    private Integer yearOfRelease;
    private String genre;
    private Integer runningTime;
}
