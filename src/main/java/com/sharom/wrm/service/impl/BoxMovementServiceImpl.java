package com.sharom.wrm.service.impl;

import com.sharom.wrm.config.CustomUserDetails;
import com.sharom.wrm.entity.*;
import com.sharom.wrm.mapper.BoxEventMapper;
import com.sharom.wrm.payload.box.BoxEventDTO;
import com.sharom.wrm.repo.BoxEventRepo;
import com.sharom.wrm.repo.BoxRepo;
import com.sharom.wrm.repo.ShipmentRepo;
import com.sharom.wrm.service.BoxMovementService;
import com.sharom.wrm.utils.Page2DTO;
import com.sharom.wrm.utils.PageDTO;
import com.sharom.wrm.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class BoxMovementServiceImpl implements BoxMovementService {

    private final ShipmentRepo shipmentRepo;
    private final BoxRepo boxRepo;
    private final BoxEventRepo boxEventRepo;
    private final BoxEventMapper boxEventMapper;


    private final Map<BoxAction, BiConsumer<String, String>> actions = Map.of(
            BoxAction.LOAD, this::loadToTruck,
            BoxAction.UNLOAD, this::unload,
            BoxAction.RELOAD, this::reloadToAnotherTruck,
            BoxAction.CUSTOMS, this::arrivedAtCustoms
    );


    @Override
    public void moveBox(String boxId, String shipmentNumber, BoxAction action) {
        this.actions.get(action).accept(boxId, shipmentNumber);
    }

    @Override
    public PageDTO<BoxEvent> getHistory(String boxId, Pageable pageable) {
        return Page2DTO.tPageDTO(boxEventRepo.findByBoxIdOrderByEventTimeAsc(boxId, pageable));
    }

    @Override
    public BoxEvent getLastEvent(String boxId) {
        return boxEventRepo.findTopByBoxIdOrderByEventTimeDesc(boxId)
                .orElseThrow(() -> new IllegalStateException("Box has no movement history"));
    }

    @Override
    public PageDTO<BoxEventDTO> getHistoryByShipment(String shipmentNumber, Pageable pageable) {

        return Page2DTO.tPageDTO(boxEventRepo.findByBoxIdOrderByEventTimeAsc(shipmentNumber, pageable)
                .map(boxEventMapper::toDto));
    }

    @Override
    public PageDTO<BoxEventDTO> getLastEventByShipment(String shipmentNumber, Pageable pageable) {
        return Page2DTO.tPageDTO(boxEventRepo.findLastLoadedEventsByShipment(shipmentNumber, pageable)
                .map(boxEventMapper::toDto));
    }



    private void loadToTruck(String boxId, String shipmentNumber) {

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


    private void unload(String boxId, String shipmentNumber) {

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



    private void reloadToAnotherTruck(String boxId, String shipmentNumber) {
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


    private void arrivedAtCustoms(String boxId, String shipmentNumber) {

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

        CustomUserDetails userDetails = SecurityUtils.currentUser();

        BoxEvent event = new BoxEvent();
        event.setBox(box);
        event.setShipmentNumber(shipment.getShipmentNumber());
        event.setType(type);
        event.setLocationType(locationType);
        event.setLocationId(userDetails.getLocationId());   // warehouseId / truckId / customsId
        event.setEventTime(LocalDateTime.now());
        event.setOperatorId(userDetails.getId());

        event.setFrom(userDetails.getLocationId());


        boxEventRepo.save(event);
    }


}
