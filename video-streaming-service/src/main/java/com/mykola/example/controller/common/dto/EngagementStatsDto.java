package com.mykola.example.controller.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EngagementStatsDto {
    private Integer impressions;
    private Integer views;
}
