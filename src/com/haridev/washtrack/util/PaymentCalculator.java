package com.haridev.washtrack.util;

import com.haridev.washtrack.enums.VehicleType;
import com.haridev.washtrack.enums.WashType;

import java.util.HashMap;
import java.util.Map;

public class PaymentCalculator {
    private static final Map<String, Double> PRICE_MAP = new HashMap<>();

    private PaymentCalculator() {
    }

    static {
        PRICE_MAP.put("BODY_WASH:TWO_WHEELER", 100.0);
        PRICE_MAP.put("BODY_WASH:THREE_WHEELER", 250.0);
        PRICE_MAP.put("BODY_WASH:FOUR_WHEELER", 300.0);
        PRICE_MAP.put("BODY_WASH:HEAVY_VEHICLE", 500.0);
        PRICE_MAP.put("COMPLETE_WASH:TWO_WHEELER", 180.0);
        PRICE_MAP.put("COMPLETE_WASH:THREE_WHEELER", 450.0);
        PRICE_MAP.put("COMPLETE_WASH:FOUR_WHEELER", 500.0);
        PRICE_MAP.put("COMPLETE_WASH:HEAVY_VEHICLE", 800.0);
    }

    public static Double calculate(WashType washType, VehicleType vehicleType) {
        return PRICE_MAP.getOrDefault(washType.name() + ":" + vehicleType.name(), 0.0);
    }
}
