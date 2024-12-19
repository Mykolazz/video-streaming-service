package com.mykola.example.controller.v1.api;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.mykola.example.controller.common.dto.EngagementStatsDto;
import com.mykola.example.controller.common.dto.GetVideoDetailsDto;
import com.mykola.example.controller.common.dto.PublishResponseDto;
import com.mykola.example.controller.common.dto.ResponseDTO;
import com.mykola.example.controller.common.dto.ResponseListDTO;
import com.mykola.example.controller.common.dto.UpdateMetadataDto;
import com.mykola.example.controller.common.dto.VideoSearchDto;
import com.mykola.example.validation.annotation.ValidImportFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface VideoApi {

    @Operation(summary = "Publish video")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Video published"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseDTO<PublishResponseDto> publish(@Parameter(description = "user id header") @NotBlank String userId,
                                            @Parameter(description = "file") @ValidImportFile MultipartFile multipartFile);

    @Operation(summary = "Update metadata")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Video not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseDTO<GetVideoDetailsDto> updateMetadata(@Parameter @Positive Long id,
                                                   @Parameter @NotNull UpdateMetadataDto updateMetadataDto);

    @Operation(summary = "Delist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Video not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    void delist(@Parameter @Positive Long id);

    @Operation(summary = "get video by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Video not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseDTO<GetVideoDetailsDto> getById(@Parameter @Positive Long id);

    @Operation(summary = "Get content")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Video not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseEntity<Resource> getContent(@Parameter @Positive Long id);

    @Operation(summary = "Get all available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseListDTO<GetVideoDetailsDto> getAllAvailable();

    @Operation(summary = "Search by params")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseListDTO<GetVideoDetailsDto> searchVideos(@Parameter @NotNull VideoSearchDto searchDto);

    @Operation(summary = "Get stats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "404", description = "Video not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseDTO<EngagementStatsDto> getEngagementStats(@Parameter @Positive Long id);
}
