package com.mykola.example.controller.v1;

import static com.mykola.example.util.Constants.USER_ID_HEADER;
import static java.lang.Boolean.TRUE;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.controller.common.dto.EngagementStatsDto;
import com.mykola.example.controller.common.dto.GetVideoDetailsDto;
import com.mykola.example.controller.common.dto.PublishResponseDto;
import com.mykola.example.controller.common.dto.ResponseDTO;
import com.mykola.example.controller.common.dto.ResponseListDTO;
import com.mykola.example.controller.common.dto.UpdateMetadataDto;
import com.mykola.example.controller.common.dto.VideoSearchDto;
import com.mykola.example.controller.v1.api.VideoApi;
import com.mykola.example.service.VideoService;
import com.mykola.example.validation.annotation.ValidImportFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(value = "/api/v1/videos")
public class VideoController implements VideoApi {

    private final VideoService videoService;

    /**
     * Publishes a new video with the given file and associated user ID.
     *
     * @param userId The ID of the user performing the publishing action.
     * @param file The uploaded video file.
     * @return A response DTO containing the publishing response with video ID and size.
     */
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<PublishResponseDto> publish(@RequestHeader(USER_ID_HEADER) @NotBlank String userId,
                                                   @RequestPart("file") @ValidImportFile MultipartFile file) {
        log.info("Start request to publish new video");
        return new ResponseDTO<>(videoService.publish(file, userId));
    }

    /**
     * Updates video metadata for a given video ID.
     *
     * @param id The ID of the video being updated.
     * @param updateMetadataDto DTO containing new metadata values.
     * @return A response DTO with updated video details.
     */
    @Override
    @PutMapping("/{id}")
    public ResponseDTO<GetVideoDetailsDto> updateMetadata(@PathVariable("id") @Positive Long id,
                                                          @RequestBody @NotNull UpdateMetadataDto updateMetadataDto) {
        log.info("Start request to update video metadata");
        return new ResponseDTO<>(videoService.updateMetadata(id, updateMetadataDto));
    }

    /**
     * Delists a video by setting its 'active' status to false.
     *
     * @param id The video ID to be delisted.
     */
    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delist(@PathVariable("id") @Positive Long id) {
        log.info("Start request to delist video");
        videoService.delist(id);
    }

    /**
     * Retrieves details of a single video by its ID.
     *
     * @param id The ID of the video to retrieve.
     * @return A response DTO with video details.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseDTO<GetVideoDetailsDto> getById(@PathVariable("id") @Positive Long id) {
        log.info("Start request to get video by id {}", id);
        return new ResponseDTO<>(videoService.getById(id));
    }

    /**
     * Streams the video content for a given video ID.
     *
     * @param id The ID of the video to stream.
     * @return A ResponseEntity containing the video stream as a Resource.
     */
    @Override
    @GetMapping("/{id}/play")
    public ResponseEntity<Resource> getContent(@PathVariable("id") @Positive Long id) {
        log.info("Start request to start streaming video with id {}", id);
        var content = videoService.getVideoStream(id);
        return ResponseEntity.ok()
                .header("Content-Type", "video/mp4")
                .header("Content-Disposition", String.format("inline; filename=\"video-%s.mp4\"", id))
                .body(content);
    }

    /**
     * Fetches a list of all available videos that are marked as active.
     *
     * @return A ResponseListDTO containing details of all available videos.
     */
    @Override
    @GetMapping("/available-list")
    public ResponseListDTO<GetVideoDetailsDto> getAllAvailable() {
        log.info("Start request to get all available videos list");
        return new ResponseListDTO<>(videoService.getAll(TRUE));
    }

    /**
     * Searches videos based on provided criteria contained in VideoSearchDto.
     *
     * @param searchDto DTO containing search parameters such as title and director.
     * @return A ResponseListDTO containing the search results.
     */
    @Override
    @PostMapping("/search")
    public ResponseListDTO<GetVideoDetailsDto> searchVideos(@RequestBody @NotNull VideoSearchDto searchDto) {
        log.info("Start request to search videos by params: [{}]", searchDto);
        return new ResponseListDTO<>(videoService.search(searchDto));
    }

    /**
     * Retrieves engagement statistics for a given video ID, such as views and impressions.
     *
     * @param id The video ID for which engagement stats are requested.
     * @return A response DTO with engagement statistics.
     */
    @Override
    @GetMapping("/{id}/engagements")
    public ResponseDTO<EngagementStatsDto> getEngagementStats(@PathVariable("id") @Positive Long id) {
        log.info("Start request to publish new video");
        return new ResponseDTO<>(videoService.getEngagementStats(id));
    }
}
