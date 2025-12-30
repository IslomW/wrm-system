package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.Box;
import com.sharom.wrm.entity.BoxStatus;
import com.sharom.wrm.entity.Shipment;
import com.sharom.wrm.entity.ShipmentStatus;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.ShipmentRepo;
import com.sharom.wrm.service.BoxService;
import com.sharom.wrm.service.ShipmentService;
import com.sharom.wrm.utils.ShipmentNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {


    private final ShipmentRepo shipmentRepository;
    private final BoxRepo boxRepository;
    private final BoxService boxService;

    @Override
    public Shipment createShipment() {
        Shipment shipment = new Shipment();
        shipment.setShipmentNumber(ShipmentNumberGenerator.next());
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setBoxes(new ArrayList<>());

        return shipmentRepository.save(shipment);
    }

    @Override
    public void addBoxToShipment(String shipmentId, String boxId) {
        Shipment shipment = getById(shipmentId);

        if (shipment.getStatus() != ShipmentStatus.CREATED) {
            throw new IllegalStateException("Cannot add boxes after dispatch");
        }

        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new RuntimeException("Box not found"));

        if (shipment.getBoxes().contains(box)) {
            return; // already added
        }

        shipment.getBoxes().add(box);

    }

    @Override
    public void removeBoxFromShipment(String shipmentId, String boxId) {

        Shipment shipment = getById(shipmentId);

        if (shipment.getStatus() != ShipmentStatus.CREATED) {
            throw new IllegalStateException("Cannot remove boxes after dispatch");
        }

        shipment.getBoxes()
                .removeIf(box -> box.getId().equals(boxId));

    }

    @Override
    public void dispatchShipment(String shipmentId) {


        Shipment shipment = getById(shipmentId);

        if (shipment.getBoxes().isEmpty()) {
            throw new IllegalStateException("Cannot dispatch empty shipment");
        }

        shipment.setStatus(ShipmentStatus.DISPATCHED);

        // optional: update box statuses
        for (Box box : shipment.getBoxes()) {
            boxService.changeStatus(
                    box.getId(),
                    BoxStatus.SHIPPED,
                    "SYSTEM"
            );
        }

    }

    @Override
    public void arriveShipment(String shipmentId) {

        Shipment shipment = getById(shipmentId);

        if (shipment.getStatus() != ShipmentStatus.DISPATCHED) {
            throw new IllegalStateException("Shipment is not dispatched");
        }

        shipment.setStatus(ShipmentStatus.ARRIVED);

    }

    @Override
    @Transactional(readOnly = true)
    public Shipment getById(String shipmentId) {
        return shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

    }
}
