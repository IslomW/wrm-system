package com.sharom.wrm.common.util;

import java.time.Year;

public class ShipmentNumberGenerator {
    public static String next() {
        return "SHP-" +
                Year.now().getValue() + "-" +
                System.currentTimeMillis();
    }
}
