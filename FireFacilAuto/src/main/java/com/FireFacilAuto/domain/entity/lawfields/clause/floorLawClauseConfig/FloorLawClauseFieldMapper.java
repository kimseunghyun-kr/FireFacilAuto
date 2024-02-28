package com.FireFacilAuto.domain.entity.lawfields.clause.floorLawClauseConfig;

import java.util.concurrent.ConcurrentHashMap;

public class FloorLawClauseFieldMapper {

    private static final ConcurrentHashMap<String, String> fieldMap = new ConcurrentHashMap<>();
    static {
        fieldMap.put("floorClassification", "floorClassification");
        fieldMap.put("floorSpecification", "floorSpecification");
        fieldMap.put("floorNo", "floorNo");
        fieldMap.put("isUnderGround", "isUnderGround");
        fieldMap.put("floorAreaSum", "floorArea");
        fieldMap.put("floorAreaThreshold", "floorArea");
        fieldMap.put("floorMaterial", "floorMaterial");
        fieldMap.put("floorWindowAvailability", "floorWindowAvailability");
    }

    public static String getFloorLawfieldTargetField(String lawfield) {
        return fieldMap.get(lawfield);
    }

}
