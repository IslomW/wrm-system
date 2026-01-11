package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxStatus;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.service.BoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoxServiceImpl implements BoxService {

    private final BoxRepo boxRepository;

    @Override
    @Transactional(readOnly = true)
    public Box getById(String boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));
    }

}
