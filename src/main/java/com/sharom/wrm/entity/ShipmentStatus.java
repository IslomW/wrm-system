package com.sharom.wrm.entity;

public enum ShipmentStatus {

    DRAFT,
    READY_FOR_LOADING,
    IN_TRANSIT,         // хотя бы одна коробка загружена
    COMPLETED,          // все коробки дошли
    CANCELLED

//    CREATED,
//    BOOKED,
//    CONFIRMED,
//    PACKED,
//    READY_FOR_PICKUP,
//
//    PICKED_UP,
//    EXPORT_CUSTOMS_CLEARANCE,
//    EXPORT_CUSTOMS_HOLD,
//    DEPARTED_ORIGIN,
//
//    IN_TRANSIT,
//    ARRIVED_TRANSIT_COUNTRY,
//    DEPARTED_TRANSIT_COUNTRY,
//
//    ARRIVED_DESTINATION,
//    IMPORT_CUSTOMS_CLEARANCE,
//    IMPORT_CUSTOMS_HOLD,
//    RELEASED_FROM_CUSTOMS,
//
//    HANDOVER_TO_LOCAL_CARRIER,
//    OUT_FOR_DELIVERY,
//    DELIVERED,
//
//    DELIVERY_FAILED,
//    RETURN_IN_TRANSIT,
//    RETURNED_TO_SENDER,
//
//    CANCELLED,
//    LOST,
//    DAMAGED
}
