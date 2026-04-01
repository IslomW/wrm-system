package com.sharom.wrm.modules.inventory.service;

import com.sharom.wrm.modules.inventory.model.dto.BoxDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupDTO;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoxGroupService {
    BoxGroupResponseDTO createGroup(
            String orderId,
            BoxGroupDTO dto
    );

    void addBoxToGroup(String groupId, String boxId);

    void removeBoxFromGroup(String groupId, String boxId);

    List<BoxDTO> getBoxes(String groupId);

    List<String> uploadPhotosToGroup(String boxGroupId, List<MultipartFile> files);
}
