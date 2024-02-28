package com.FireFacilAuto.domain.entity.lawfields.clause.floorLawClauseConfig;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class floorLawClauses {
    private static final ConcurrentHashMap<String, Class<?>> fieldMap = new ConcurrentHashMap<>();

    static {
        fieldMap.put("floorClassification", Integer.class);
        fieldMap.put("floorSpecification", Integer.class);
        fieldMap.put("floorNo", Integer.class);
        fieldMap.put("isUnderGround", Boolean.class);
        fieldMap.put("floorAreaSum", Double.class);
        fieldMap.put("floorAreaThreshold", Double.class);
        fieldMap.put("floorMaterial", Integer.class);
        fieldMap.put("floorWindowAvailability", Boolean.class);
    }

    public static Class<?> getFloorLawClassToken(String field) {
        return fieldMap.get(field);
    }

}
