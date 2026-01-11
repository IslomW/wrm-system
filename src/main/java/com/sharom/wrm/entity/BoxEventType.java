package com.sharom.wrm.entity;

public enum BoxEventType {
    PLANNED,              // запланирован к отгрузке
    LOADED_TO_TRUCK,      // загружен в truck
    DEPARTED,             // truck выехал
    ARRIVED_AT_STOP,      // прибыл на остановку
    UNLOADED,             // выгружен
    RELOADED_TO_TRUCK,    // перегружен в другой truck
    ARRIVED_CUSTOMS,     // прибыл на таможню
    LOST,                 // потерян
    DAMAGED               // повреждён
}
