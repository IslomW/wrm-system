package com.sharom.wrm.service.impl;

import com.sharom.wrm.payload.BoxHistoryResponse;
import com.sharom.wrm.service.BoxHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoxHistoryServiceImpl implements BoxHistoryService {
    @Override
    public BoxHistoryResponse getFullHistory(String boxId) {
        return null;
    }
}
