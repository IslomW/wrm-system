package com.sharom.wrm.service;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.payload.BoxDTO;
import com.sharom.wrm.payload.BoxGroupDTO;
import com.sharom.wrm.payload.BoxGroupResponseDTO;

import java.util.List;

public interface BoxGroupService {
    BoxGroupResponseDTO createGroup(
            String orderId,
            BoxGroupDTO dto
    );

    void addBoxToGroup(String groupId, String boxId);

    void removeBoxFromGroup(String groupId, String boxId);

    List<BoxDTO> getBoxes(String groupId);
}
