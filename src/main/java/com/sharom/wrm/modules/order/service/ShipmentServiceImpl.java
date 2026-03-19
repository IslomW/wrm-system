package com.sharom.wrm.modules.order.service;

import com.sharom.wrm.modules.inventory.mapper.BoxMapper;
import com.sharom.wrm.modules.order.mapper.ShipmentMapper;
import com.sharom.wrm.modules.inventory.model.entity.Box;
import com.sharom.wrm.modules.inventory.model.entity.BoxGroup;
import com.sharom.wrm.modules.order.model.entity.Shipment;
import com.sharom.wrm.modules.order.model.entity.ShipmentStatus;
import com.sharom.wrm.modules.inventory.model.dto.BoxGroupResponseDTO;
import com.sharom.wrm.modules.order.model.dto.ShipmentPlanedDTO;
import com.sharom.wrm.modules.order.model.dto.ShipmentResponseDTO;
import com.sharom.wrm.modules.inventory.repository.BoxGroupRepo;
import com.sharom.wrm.modules.inventory.repository.BoxRepo;
import com.sharom.wrm.modules.order.repository.ShipmentRepo;
import com.sharom.wrm.common.util.Page2DTO;
import com.sharom.wrm.common.util.PageDTO;
import com.sharom.wrm.common.util.ShipmentNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {


    private final BoxGroupRepo boxGroupRepository;
    private final ShipmentRepo shipmentRepository;
    private final BoxRepo boxRepository;
    private final ShipmentMapper shipmentMapper;
    private final BoxMapper boxMapper;

    @Override
    public ShipmentResponseDTO createShipment(List<String> boxGroupIds) {
        List<BoxGroup> boxGroups = boxGroupRepository.findAllById(boxGroupIds);

        if (boxGroups.isEmpty()) {
            throw new IllegalArgumentException("BoxGroups not found");
        }

        Shipment shipment = new Shipment();
        shipment.setShipmentNumber(ShipmentNumberGenerator.next());
        shipment.setStatus(ShipmentStatus.DRAFT);
        shipment.setPlannedGroups(boxGroups);


        return shipmentMapper.toShipmentResponseDTO(shipmentRepository.save(shipment));
    }

    @Override
    public void lockShipment(String shipmentNumber) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        if (shipment.getPlannedGroups().isEmpty()) {
            throw new IllegalStateException("Shipment has no planned boxes");
        }

        shipment.setStatus(ShipmentStatus.READY_FOR_LOADING);
    }

    @Override
    @Transactional
    public void addBoxToShipment(String shipmentNumber, String boxId) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        if (shipment.getStatus() != ShipmentStatus.DRAFT) {
            throw new IllegalStateException("Cannot add boxes after dispatch");
        }

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        BoxGroup boxGroup = box.getBoxGroup();

        if (!shipment.getPlannedGroups().contains(boxGroup)) {
            shipment.getPlannedGroups().add(boxGroup);
        }

    }

    @Override
    public void addGroupBoxToShipment(String shipmentNumber, String groupBoxId) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        BoxGroup boxGroup = boxGroupRepository.findById(groupBoxId)
                .orElseThrow(()-> new IllegalStateException("Group box not found."));

        shipment.setPlannedGroups(List.of(boxGroup));
    }

    @Override
    @Transactional
    public void addGroupBoxesToShipment(String shipmentNumber, String groupId) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);
        if (shipment.getStatus() != ShipmentStatus.DRAFT) {
            throw new IllegalStateException("Cannot add boxes after dispatch");
        }

        BoxGroup group = boxGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("BoxGroup not found"));

        if (shipment.getPlannedGroups().contains(group)) {
            throw new IllegalStateException("BoxGroup already added to shipment");
        }

        shipment.getPlannedGroups().add(group);

    }

    @Override
    @Transactional
    public void removeGroupBoxesFromShipment(String shipmentNumber, String groupId) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        if (shipment.getStatus() != ShipmentStatus.DRAFT) {
            throw new IllegalStateException("Cannot remove boxes after dispatch");
        }

        shipment.getPlannedGroups().removeIf(group -> group.getId().equals(groupId));
    }


    @Override
    @Transactional(readOnly = true)
    public ShipmentResponseDTO getByNumber(String shipmentNumber) {
        ShipmentResponseDTO responseDTO = shipmentMapper
                .toShipmentResponseDTO(getShipmentByNumber(shipmentNumber));

        return responseDTO;

    }

    @Override
    public PageDTO<ShipmentResponseDTO> getAllShipments(Pageable pageable) {
        return Page2DTO.tPageDTO(shipmentRepository.findAll(pageable).map(shipmentMapper::toShipmentResponseDTO));
    }

    @Override
    public ShipmentPlanedDTO getShipmentPlaned(String shipmentNumber) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        List<BoxGroupResponseDTO> groupResponses =
                shipment.getPlannedGroups().stream()
                        .map(boxGroup -> new BoxGroupResponseDTO(
                                boxGroup.getId(),
                                boxGroup.getDescription(),
                                boxGroup.getQuantity(),
                                boxMapper.toDtoList(boxGroup.getBoxes()),
                                boxGroup.getPhotoUrls()
                        ))
                        .toList();

        return new ShipmentPlanedDTO(
                shipmentNumber,
                shipment.getStatus(),
                groupResponses);
    }

    private Shipment getShipmentByNumber(String s) {
        return shipmentRepository.findByShipmentNumber(s)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }
}
