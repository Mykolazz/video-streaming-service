package com.mykola.example.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.controller.common.dto.EngagementStatsDto;
import com.mykola.example.controller.common.dto.GetVideoDetailsDto;
import com.mykola.example.controller.common.dto.PublishResponseDto;
import com.mykola.example.controller.common.dto.UpdateMetadataDto;
import com.mykola.example.controller.common.dto.VideoSearchDto;

public interface VideoService {

    PublishResponseDto publish(MultipartFile file, String userId);
    GetVideoDetailsDto updateMetadata(Long id, UpdateMetadataDto updateMetadataDto);
    void delist(Long id);

    List<GetVideoDetailsDto> search(VideoSearchDto searchDto);

    GetVideoDetailsDto getById(Long id);

    Resource getVideoStream(Long id);

    List<GetVideoDetailsDto> getAll(boolean onlyAvailable);

    EngagementStatsDto getEngagementStats(Long id);
}
