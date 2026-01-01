package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.*;
import com.sharom.wrm.payload.BoxGroupDTO;
import com.sharom.wrm.repo.BoxGroupRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.OrderRepo;
import com.sharom.wrm.service.BoxGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sharom.wrm.entity.BoxStatus.CREATED;

@Service
@RequiredArgsConstructor
public class BoxGroupServiceImpl implements BoxGroupService {

    private final BoxGroupRepo boxGroupRepo;
    private final BoxRepo boxRepo;
    private final OrderRepo orderRepo;

    @Override
    @Transactional
    public BoxGroup createGroup(String orderId, BoxGroupDTO dto) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

//        if (dto.quantity() <= 0) {
//            throw new IllegalArgumentException("Quantity must be greater than 0");
//        }
//
//        Warehouse warehouse = warehouseRepo.findById(dto.warehouseId())
//                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        BoxGroup boxGroup = new BoxGroup();

        boxGroup.setOrder(order);
        boxGroup.setDescription(dto.description());
        boxGroup.setWeight(dto.weight());
        boxGroup.setLength(dto.length());
        boxGroup.setWidth(dto.width());
        boxGroup.setHeight(dto.height());
        boxGroup.setBoxType(dto.boxType());
        boxGroup.setQuantity(dto.quantity());

        BoxGroup savedGroup = boxGroupRepo.save(boxGroup);

        List<Box> boxes = new ArrayList<>();

        Warehouse warehouse = new Warehouse();//ishchi ishlidigan ombor

        for (int i = 1; i <= dto.quantity(); i++) {
            Box box = new Box();
            box.setBoxGroup(savedGroup);
            box.setCurrentWarehouse(warehouse);
            box.setStatus(CREATED);
            box.setBoxNumber("BoxNumber");
            box.setQrCode("QrCode");
            boxes.add(box);
        }

        savedGroup.setBoxes(boxes);

        return savedGroup;
    }

    @Override
    @Transactional
    public void addBoxToGroup(String groupId, String boxId) {
        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        Box box = boxRepo.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        // Если коробка уже в другой группе
        if (box.getBoxGroup() != null && !box.getBoxGroup().getId().equals(groupId)) {
            throw new RuntimeException("Box already belongs to another group");
        }

        // Связываем
        box.setBoxGroup(group);
        boxRepo.save(box);

        // Обновляем список коробок в группе (опционально, если есть mappedBy)
        group.getBoxes().add(box);
        boxGroupRepo.save(group);
    }

    @Override
    @Transactional
    public void removeBoxFromGroup(String groupId, String boxId) {
        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        Box box = boxRepo.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        if (box.getBoxGroup() == null || !box.getBoxGroup().getId().equals(groupId)) {
            throw new RuntimeException("Box does not belong to this group");
        }

        // Отвязываем
        box.setBoxGroup(null);
        boxRepo.save(box);

        // Обновляем список в группе
        group.getBoxes().removeIf(b -> b.getId().equals(boxId));
        boxGroupRepo.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Box> getBoxes(String groupId) {
        BoxGroup group = boxGroupRepo.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        // Возвращаем копию списка, чтобы фронт не изменял оригинал
        return new ArrayList<>(group.getBoxes());
    }
}
