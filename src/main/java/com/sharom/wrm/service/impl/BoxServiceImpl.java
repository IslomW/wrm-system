package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxStatus;
import com.sharom.wrm.entity.BoxStatusHistory;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.BoxStatusHistoryRepo;
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
    private final BoxStatusHistoryRepo historyRepository;

    @Override
    public void changeStatus(String boxId, BoxStatus newStatus, String changedBy) {

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        // FSM validation
        BoxStatusTransitions.validate(box.getStatus(), newStatus);

        // change state
        box.setStatus(newStatus);

        // audit log
        BoxStatusHistory history = new BoxStatusHistory();
        history.setBox(box);
        history.setBoxStatus(newStatus);
        history.setUpdatedBy(changedBy);
        history.setUpdatedAt(LocalDateTime.now());

        historyRepository.save(history);
        // optimistic locking via @Version protects from double scan
    }

    @Override
    @Transactional(readOnly = true)
    public Box getById(String boxId) {
        return boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoxStatusHistory> getStatusHistory(String boxId) {
        return historyRepository
                .findByBoxIdOrderByUpdatedAtDesc(boxId);
    }
}
