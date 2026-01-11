package com.sharom.wrm.service.impl;

import com.sharom.wrm.entity.BoxStatus;

import java.util.Map;
import java.util.Set;

public final class BoxStatusTransitions {

//    private static final Map<BoxStatus, Set<BoxStatus>> ALLOWED = Map.of(
//            BoxStatus.CREATED, Set.of(BoxStatus.PACKED),
//            BoxStatus.PACKED, Set.of(BoxStatus.IN_WAREHOUSE),
//            BoxStatus.IN_WAREHOUSE, Set.of(BoxStatus.SHIPPED),
//            BoxStatus.SHIPPED, Set.of(BoxStatus.DELIVERED),
//            BoxStatus.DELIVERED, Set.of(),
//            BoxStatus.LOST, Set.of()
//    );
//
//    private BoxStatusTransitions() {}
//
//    public static void validate(BoxStatus from, BoxStatus to) {
//        if (from == null) {
//            throw new IllegalStateException("Box has no status");
//        }
//        if (!ALLOWED.getOrDefault(from, Set.of()).contains(to)) {
//            throw new IllegalStateException(
//                    "Invalid box status transition: " + from + " -> " + to
//            );
//        }
//    }
}
