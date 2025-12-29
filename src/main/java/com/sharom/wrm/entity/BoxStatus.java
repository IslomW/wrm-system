package com.sharom.wrm.entity;

public enum BoxStatus {
    CREATED_AND_RECEIVED, // принята на склад (отсканирован QR)
    IN_CHINA_VEHICLE,
    IN_QASHQAR,
    IN_FOREIGN_VEHICLE,
    IN_TRANSIT,     // в пути (Кашгар / КР / РУ)
    AT_CUSTOMS,     // на таможне
    RELEASED, // выпущена таможней
    RECIVED_IN_TASHKENT,
    DELIVERED,      // доставлена клиенту
    CLOSED          // заказ закрыт
}
