package com.sharom.wrm.utils;

import java.time.Year;

public class ShipmentNumberGenerator {
    public static String next() {
        return "SHP-" +
                Year.now().getValue() + "-" +
                System.currentTimeMillis();
    }
}
