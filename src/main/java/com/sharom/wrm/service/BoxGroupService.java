package com.sharom.wrm.service;

import com.sharom.wrm.payload.box.BoxDTO;
import com.sharom.wrm.payload.box.BoxGroupDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;
import com.sharom.wrm.utils.PageDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoxGroupService {

    BoxGroupDTO createGroup(
            String orderId,
            BoxGroupDTO dto,
            List<MultipartFile> photos
    );

    void addBoxToGroup(String groupId, String boxId);

    void removeBoxFromGroup(String groupId, String boxId);

    List<BoxDTO> getBoxes(String groupId);

    PageDTO<BoxGroupDTO> getGroupBox(Pageable pageable);
}
