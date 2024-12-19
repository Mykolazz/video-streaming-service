package com.mykola.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mykola.example.client.storage.common.dto.ClientSaveResponseDto;
import com.mykola.example.controller.common.dto.EngagementStatsDto;
import com.mykola.example.controller.common.dto.GetVideoDetailsDto;
import com.mykola.example.controller.common.dto.PublishResponseDto;
import com.mykola.example.controller.common.dto.UpdateMetadataDto;
import com.mykola.example.entity.Video;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contentExternalId", source = "clientSaveResponseDto.id")
    Video toEntity(ClientSaveResponseDto clientSaveResponseDto, String userId);

    GetVideoDetailsDto toGetVideoDetailsDto(Video video);

    PublishResponseDto toPublishResponseDto(Video video);

    Video updateMetadata(@MappingTarget Video entity, UpdateMetadataDto updateMetadataDto);

    List<GetVideoDetailsDto> toGetVideoDetailsDtoList(List<Video> foundedVideos);

    EngagementStatsDto toEngagementStatsDto(Video video);
}
