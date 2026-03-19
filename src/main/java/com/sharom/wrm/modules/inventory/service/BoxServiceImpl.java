package com.sharom.wrm.modules.inventory.service;

import com.sharom.wrm.modules.inventory.model.entity.Box;
import com.sharom.wrm.modules.inventory.repository.BoxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
