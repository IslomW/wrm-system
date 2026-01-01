package com.sharom.wrm.service;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxGroup;
import com.sharom.wrm.payload.BoxGroupDTO;

import java.util.List;

public interface BoxGroupService {
    BoxGroup createGroup(
            String orderId,
            BoxGroupDTO dto
    );

    void addBoxToGroup(String groupId, String boxId);

    void removeBoxFromGroup(String groupId, String boxId);

    List<Box> getBoxes(String groupId);
}
