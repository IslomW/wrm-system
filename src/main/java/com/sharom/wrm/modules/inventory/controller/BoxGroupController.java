package com.sharom.wrm.modules.inventory.controller;

import com.sharom.wrm.common.response.ApiResponse;
import com.sharom.wrm.common.response.ResponseFactory;
import com.sharom.wrm.modules.inventory.model.dto.BoxDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;
import com.sharom.wrm.modules.inventory.service.BoxGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/box-groups")
@RequiredArgsConstructor
public class BoxGroupController {

    private final BoxGroupService boxGroupService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ApiResponse<BoxGroupResponseDTO>> createGroup(
            @PathVariable String orderId,
            @RequestBody BoxGroupDTO dto
    ) {
        BoxGroupResponseDTO responseDTO = boxGroupService.createGroup(orderId, dto);
        return ResponseFactory.created(responseDTO);
    }


    @PostMapping("/{groupId}/photos")
    public ResponseEntity<ApiResponse<List<String>>> uploadGroupPhotos(
            @PathVariable String groupId,
            @RequestPart("photos") List<MultipartFile> photos
    ) {
        List<String> photoUrls = boxGroupService.uploadPhotosToGroup(groupId, photos);
        return ResponseFactory.created(photoUrls);
    }

    @PostMapping("/{groupId}/boxes/{boxId}")
    public ResponseEntity<Void> addBoxToGroup(
            @PathVariable String groupId,
            @PathVariable String boxId
    ) {
        boxGroupService.addBoxToGroup(groupId, boxId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/boxes/{boxId}")
    public ResponseEntity<Void> removeBoxFromGroup(
            @PathVariable String groupId,
            @PathVariable String boxId
    ) {
        boxGroupService.removeBoxFromGroup(groupId, boxId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/boxes")
    public ResponseEntity<ApiResponse<List<BoxDTO>>> getBoxes(
            @PathVariable String groupId
    ) {
        List<BoxDTO> boxes = boxGroupService.getBoxes(groupId);
        return ResponseFactory.ok(boxes);
    }
}
