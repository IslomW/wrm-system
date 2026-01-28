package com.sharom.wrm.controller;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxGroup;
import com.sharom.wrm.repo.BoxGroupRepo;
import com.sharom.wrm.service.impl.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/labels")
public class LabelController {

    private final LabelService labelService;
    private final BoxGroupRepo boxGroupRepo;


}
