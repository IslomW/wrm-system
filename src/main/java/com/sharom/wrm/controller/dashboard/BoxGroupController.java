package com.sharom.wrm.controller.dashboard;

import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.service.BoxGroupService;
import com.sharom.wrm.utils.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("mobileBoxGroupController")
@RequestMapping("/api/v1/box-groups")
@RequiredArgsConstructor
public class BoxGroupController {

    private final BoxGroupService boxGroupService;

    @GetMapping()
    public ResponseEntity<PageDTO<BoxGroupDTO>> getBoxes(Pageable pageable) {
        return ResponseEntity.ok(boxGroupService.getGroupBox(pageable));
    }
}
