package com.sharom.wrm.controller.mobile;

import com.sharom.wrm.payload.box.BoxDTO;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;
import com.sharom.wrm.service.BoxGroupService;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController("dashboardBoxGroupController")
@RequestMapping("/api/v1/dashboard/box-groups")
@RequiredArgsConstructor
public class BoxGroupController {

    private final BoxGroupService boxGroupService;

    // Создать новую группу коробок
    @PostMapping("/{orderId}")
    public ResponseEntity<BoxGroupDTO> createGroup(
            @PathVariable String orderId,
            @RequestBody BoxGroupDTO dto,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos
    ) {
        BoxGroupDTO responseDTO = boxGroupService.createGroup(orderId, dto, photos);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/boxes/qr/{bogGroupId}")
    public ResponseEntity<byte[]> generateQrcode(@PathVariable String bogGroupId) {
        byte[] pdf = boxGroupService.getQrcode(bogGroupId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=box-labels-a4.pdf")
                .body(pdf);
    }

    // Добавить коробку в группу
    @PostMapping("/{groupId}/boxes/{boxId}")
    public ResponseEntity<Void> addBoxToGroup(
            @PathVariable String groupId,
            @PathVariable String boxId
    ) {
        boxGroupService.addBoxToGroup(groupId, boxId);
        return ResponseEntity.noContent().build();
    }

    // Удалить коробку из группы
    @DeleteMapping("/{groupId}/boxes/{boxId}")
    public ResponseEntity<Void> removeBoxFromGroup(
            @PathVariable String groupId,
            @PathVariable String boxId
    ) {
        boxGroupService.removeBoxFromGroup(groupId, boxId);
        return ResponseEntity.noContent().build();
    }

    // Получить список коробок в группе
    @GetMapping("/{groupId}/boxes")
    public ResponseEntity<List<BoxDTO>> getBoxes(
            @PathVariable String groupId
    ) {
        List<BoxDTO> boxes = boxGroupService.getBoxes(groupId);
        return ResponseEntity.ok(boxes);
    }
}
