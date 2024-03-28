package com.FireFacilAuto.domain.entity.floors;

import java.util.concurrent.ConcurrentHashMap;

public class PossibleFloorFields {


    private static final ConcurrentHashMap<String, Class<?>> fieldMap = new ConcurrentHashMap<>();

    static {
        fieldMap.put("floorNo", Integer.class);
        fieldMap.put("isUnderGround", Boolean.class);
        fieldMap.put("floorClassification", Integer.class);
        fieldMap.put("floorSpecification", Integer.class);
        fieldMap.put("floorArea",Double.class);
        fieldMap.put("floorMaterial",Integer.class);
        fieldMap.put("floorWindowAvailability",Boolean.class);
        fieldMap.put("extraFacility", String.class);
    }

    public static Class<?> getFloorClass(String field) {
        return fieldMap.get(field);
    }

}
