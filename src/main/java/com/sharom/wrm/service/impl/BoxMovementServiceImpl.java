package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.*;
import com.sharom.wrm.repo.BoxEventRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.ShipmentRepo;
import com.sharom.wrm.service.BoxMovementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxMovementServiceImpl implements BoxMovementService {

    private final ShipmentRepo shipmentRepo;
    private final BoxRepo boxRepo;
    private final BoxEventRepo boxEventRepo;


    @Override
    public void loadToTruck(String boxId, String shipmentNumber) {

        Box box = getBoxById(boxId);

        Shipment shipment = getShipmentByNumber(shipmentNumber);

        validateShipmentReady(shipment);
        validateBoxAllowed(box, shipment);

        saveEvent(
                box,
                shipment,
                BoxEventType.LOADED_TO_TRUCK,
                LocationType.TRUCK
        );

    }

    @Override
    public void unload(String boxId, String shipmentNumber) {

        Box box = getBoxById(boxId);

        Shipment shipment = getShipmentByNumber(shipmentNumber);

        validateCurrentLocation(box, LocationType.TRUCK);

        saveEvent(
                box,
                shipment,
                BoxEventType.UNLOADED,
                LocationType.WAREHOUSE
        );
    }


    @Override
    public void reloadToAnotherTruck(String boxId, String shipmentNumber) {
        Box box = getBoxById(boxId);

        Shipment shipment = getShipmentByNumber(shipmentNumber);

        validateCurrentLocation(box, LocationType.WAREHOUSE);

        saveEvent(
                box,
                shipment,
                BoxEventType.RELOADED_TO_TRUCK,
                LocationType.TRUCK
        );
    }

    @Override
    public void arrivedAtCustoms(String boxId, String shipmentNumber) {

        Box box = getBoxById(boxId);

        Shipment shipment = getShipmentByNumber(shipmentNumber);

        validateCurrentLocation(box, LocationType.TRUCK);

        saveEvent(
                box,
                shipment,
                BoxEventType.ARRIVED_CUSTOMS,
                LocationType.CUSTOMS
        );
    }

    @Override
    public Page<BoxEvent> getHistory(String boxId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return boxEventRepo.findByBoxIdOrderByEventTimeAsc(boxId, pageable);
    }

    @Override
    public BoxEvent getLastEvent(String boxId) {
        return boxEventRepo.findTopByBoxIdOrderByEventTimeDesc(boxId)
                .orElseThrow(() -> new IllegalStateException("Box has no movement history"));
    }

    @Override
    public Page<BoxEvent> getHistoryByShipment(String shipmentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return boxEventRepo.findByShipmentIdOrderByEventTimeAsc(shipmentId, pageable);
    }

    @Override
    public Page<BoxEvent> getLastEventByShipment(String shipmentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return boxEventRepo.findLastLoadedEventsByShipment(shipmentId, pageable);
    }

    /* ============================
       VALIDATIONS
       ============================ */

    private void validateShipmentReady(Shipment shipment) {
        if (shipment.getStatus() != ShipmentStatus.READY_FOR_LOADING) {
            throw new IllegalStateException("Shipment not ready for loading");
        }
    }

    private void validateBoxAllowed(Box box, Shipment shipment) {
        boolean allowed = shipment.getPlannedGroups().stream()
                .flatMap(group -> group.getBoxes().stream())
                .anyMatch(plannedBox -> plannedBox.getId().equals(box.getId()));

        if (!allowed) {
            throw new IllegalStateException("Box not allowed for this shipment");
        }
    }


    private void validateCurrentLocation(Box box, LocationType expected) {
        BoxEvent last = boxEventRepo
                .findTopByBoxOrderByEventTimeDesc(box)
                .orElseThrow(() -> new IllegalStateException("Box has no movement history"));

        if (last.getLocationType() != expected) {
            throw new IllegalStateException(
                    "Invalid box location. Expected " + expected +
                            " but was " + last.getLocationType()
            );
        }
    }

    private Box getBoxById(String boxId) {
        return boxRepo.findById(boxId)
                .orElseThrow(() -> new EntityNotFoundException("Box with id " + boxId + " not found"));
    }

    private Shipment getShipmentByNumber(String shipmentNumber) {
        return shipmentRepo.findByShipmentNumber(shipmentNumber)
                .orElseThrow(() -> new EntityNotFoundException("Shipment with number " + shipmentNumber + " not found"));
    }

    /* ============================
       EVENT SAVE
       ============================ */

    private void saveEvent(
            Box box,
            Shipment shipment,
            BoxEventType type,
            LocationType locationType
//            String locationId
    ) {
        BoxEvent event = new BoxEvent();
        event.setBox(box);
        event.setShipment(shipment);
        event.setType(type);
        event.setLocationType(locationType);
//        event.setLocationId(locationId);   // warehouseId / truckId / customsId
        event.setEventTime(LocalDateTime.now());
//        event.setCreatedBy(operator.getId());

        boxEventRepo.save(event);
    }


}
