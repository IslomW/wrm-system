package com.sharom.wrm.service;

import com.sharom.wrm.payload.box.BoxDTO;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;

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
