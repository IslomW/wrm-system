package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.*;
import com.sharom.wrm.mapper.BoxMapper;
import com.sharom.wrm.mapper.ShipmentMapper;
import com.sharom.wrm.payload.box.BoxDTO;
import com.sharom.wrm.payload.box.BoxGroupResponseDTO;
import com.sharom.wrm.payload.shipment.ShipmentPlanedDTO;
import com.sharom.wrm.payload.shipment.ShipmentResponseDTO;
import com.sharom.wrm.repo.BoxGroupRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.ShipmentRepo;
import com.sharom.wrm.service.BoxService;
import com.sharom.wrm.service.ShipmentService;
import com.sharom.wrm.utils.Page2DTO;
import com.sharom.wrm.utils.PageDTO;
import com.sharom.wrm.utils.ShipmentNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                                boxGroup.getBoxGroupCode(),
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
