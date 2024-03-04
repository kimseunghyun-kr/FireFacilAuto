package com.FireFacilAuto.domain.entity.lawfields.clause;

import java.util.concurrent.ConcurrentHashMap;

public class ClauseFieldComparatorConfig {
    private static final ConcurrentHashMap<String,String> aggregationOperationSet = new ConcurrentHashMap();

    static {
        aggregationOperationSet.put("floorAreaSum","floorArea");
    }
    public static boolean isAggregationOperation(String field) {
        return aggregationOperationSet.contains(field);
    }

    public static String getTargetField(String field) {
        return aggregationOperationSet.get(field);
    }

}
