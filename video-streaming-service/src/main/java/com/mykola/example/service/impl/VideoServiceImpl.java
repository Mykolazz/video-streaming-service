package com.mykola.example.service.impl;

import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.client.storage.StorageServiceClientProxy;
import com.mykola.example.controller.common.dto.EngagementStatsDto;
import com.mykola.example.controller.common.dto.GetVideoDetailsDto;
import com.mykola.example.controller.common.dto.PublishResponseDto;
import com.mykola.example.controller.common.dto.UpdateMetadataDto;
import com.mykola.example.controller.common.dto.VideoSearchDto;
import com.mykola.example.controller.common.exception.VideoNotFoundException;
import com.mykola.example.entity.Video;
import com.mykola.example.mapper.VideoMapper;
import com.mykola.example.repository.VideoRepository;
import com.mykola.example.service.VideoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final StorageServiceClientProxy storageServiceClientProxy;

    @Override
    @Transactional
    public PublishResponseDto publish(MultipartFile file, String userId) {
        var storageResponseDto = storageServiceClientProxy.save(file);
        var videoToSave = videoMapper.toEntity(storageResponseDto, userId);
        var savedVideo = videoRepository.save(videoToSave);

        return videoMapper.toPublishResponseDto(savedVideo);
    }

    @Override
    public GetVideoDetailsDto updateMetadata(Long id, UpdateMetadataDto updateMetadataDto) {
        log.info("Updating metadata for video with id {}", id);
        var updatedVideo = videoMapper.updateMetadata(findById(id), updateMetadataDto);
        var savedVideo = videoRepository.save(updatedVideo);
        log.info("Video metadata updated successfully for video with id {}", id);

        return videoMapper.toGetVideoDetailsDto(savedVideo);
    }

    @Override
    public void delist(Long id) {
       if (existById(id)) {
           videoRepository.setActive(id, FALSE);
           log.info("Video with id {} was successfully delisted", id);
       } else throw new VideoNotFoundException(id);
    }

    @Override
    public List<GetVideoDetailsDto> search(VideoSearchDto searchDto) {
        var title = searchDto.getTitle();
        var director = searchDto.getDirector();
        List<Video> foundedVideos;

        if (isBlank(title) && isBlank(director)) {
            foundedVideos = new ArrayList<>();
        } else if (isBlank(title)) {
            foundedVideos =  videoRepository.findByDirectorContainingIgnoreCase(director);
        } else if (isBlank(director)) {
            foundedVideos =  videoRepository.findByTitleContainingIgnoreCase(title);
        } else {
            foundedVideos =  videoRepository.findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(title, director);
        }

        return videoMapper.toGetVideoDetailsDtoList(foundedVideos);
    }

    @Override
    public GetVideoDetailsDto getById(Long id) {
        var video = findById(id);

        // Increment the impression count
        video.setImpressions(video.getImpressions() + 1);

        videoRepository.save(video);

        return videoMapper.toGetVideoDetailsDto(video);
    }

    @Override
    public Resource getVideoStream(Long id) {
        var video = findById(id);

        // Increment the view count
        video.setViews(video.getViews() + 1);

        videoRepository.save(video);

        return storageServiceClientProxy.getContent(video.getContentExternalId());
    }

    @Override
    public List<GetVideoDetailsDto> getAll(boolean onlyAvailable) {
        var videos = onlyAvailable ? videoRepository.findByActiveTrue() : videoRepository.findAll();

        return videoMapper.toGetVideoDetailsDtoList(videos);
    }

    @Override
    public EngagementStatsDto getEngagementStats(Long id) {
        var video = findById(id);

        return videoMapper.toEngagementStatsDto(video);
    }

    private Video findById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Video with id {} was not found", id);
                    return new VideoNotFoundException(id);
                });
    }

    private boolean existById(Long id) {
        return videoRepository.existsById(id);
    }
}
