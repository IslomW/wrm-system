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
import com.sharom.wrm.utils.ShipmentNumberGenerator;
import lombok.RequiredArgsConstructor;
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


        return shipmentMapper.shipmentResponseDTO(shipmentRepository.save(shipment));
    }

    @Override
    public void lockShipment(String  shipmentNumber) {
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
    public void removeBoxFromShipment(String shipmentId, String boxId) {

//        Shipment shipment = getById(shipmentId);
//
//        if (shipment.getStatus() != ShipmentStatus.CREATED) {
//            throw new IllegalStateException("Cannot remove boxes after dispatch");
//        }
//
//        shipment.getBoxes()
//                .removeIf(box -> box.getId().equals(boxId));

    }

    @Override
    public void dispatchShipment(String shipmentId) {


//        Shipment shipment = getById(shipmentId);
//
//        if (shipment.getBoxes().isEmpty()) {
//            throw new IllegalStateException("Cannot dispatch empty shipment");
//        }
//
//        shipment.setStatus(ShipmentStatus.DISPATCHED);
//
//        // optional: update box statuses
//        for (Box box : shipment.getBoxes()) {
//            boxService.changeStatus(
//                    box.getId(),
//                    BoxStatus.SHIPPED,
//                    "SYSTEM"
//            );
//        }

    }

    @Override
    public void arriveShipment(String shipmentId) {

//        Shipment shipment = getById(shipmentId);
//
//        if (shipment.getStatus() != ShipmentStatus.DISPATCHED) {
//            throw new IllegalStateException("Shipment is not dispatched");
//        }
//
//        shipment.setStatus(ShipmentStatus.ARRIVED);

    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponseDTO getByNumber(String shipmentNumber) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);

        shipment.getPlannedGroups().forEach(boxGroup -> boxGroup.getBoxes().size());


        List<BoxDTO> plannedBoxes = shipment.getPlannedGroups().stream()
                .flatMap(group -> group.getBoxes().stream())
                .map(boxMapper::toDto)
                .toList();

        return new ShipmentResponseDTO(
                shipment.getShipmentNumber(),
                shipment.getStatus(),
                plannedBoxes);

    }

    @Override
    public ShipmentPlanedDTO getShipmentPlaned(String shipmentNumber) {
        Shipment shipment = getShipmentByNumber(shipmentNumber);
        shipment.getPlannedGroups().forEach(group -> group.getBoxes().size());
        List<BoxGroup> groups = shipment.getPlannedGroups();

        List<BoxGroupResponseDTO> groupResponses =
                shipment.getPlannedGroups().stream()
                        .map(group -> new BoxGroupResponseDTO(
                                group.getId(),
                                group.getDescription(),
                                group.getBoxes().size(),
                                group.getBoxes().stream()
                                        .map(boxMapper::toDto)
                                        .toList()
                        ))
                        .toList();

        return new ShipmentPlanedDTO(
                shipmentNumber,
                shipment.getStatus(),
                groupResponses);
    }

    private Shipment getShipmentByNumber(String s) {
        return shipmentRepository.findById(s)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));
    }
}
