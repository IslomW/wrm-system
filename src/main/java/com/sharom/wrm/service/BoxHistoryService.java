package com.sharom.wrm.service;

import com.sharom.wrm.payload.BoxHistoryResponse;

public interface BoxHistoryService {
    BoxHistoryResponse getFullHistory(String boxId);
}
