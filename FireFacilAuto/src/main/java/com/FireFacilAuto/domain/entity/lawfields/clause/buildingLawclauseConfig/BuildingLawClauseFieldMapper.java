package com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class BuildingLawClauseFieldMapper {
    private static final ConcurrentHashMap<String, String> fieldMap = new ConcurrentHashMap<>();
    static {
        fieldMap.put("totalFloors", "totalFloors");
        fieldMap.put("undergroundFloors", "undergroundFloors");
        fieldMap.put("overgroundFloors", "overgroundFloors");
        fieldMap.put("GFA", "GFA");
        fieldMap.put("buildingMaterial", "buildingMaterial");
        fieldMap.put("length","length");
        fieldMap.put("dateOfApproval", "dateOfApproval");
        fieldMap.put("buildingHumanCapacity", "buildingHumanCapacity");
        fieldMap.put("extraFacility", "extraFacility");
    }

    public static String getBuildingLawfieldTargetField(String lawfield) {
        return fieldMap.get(lawfield);
    }
}
