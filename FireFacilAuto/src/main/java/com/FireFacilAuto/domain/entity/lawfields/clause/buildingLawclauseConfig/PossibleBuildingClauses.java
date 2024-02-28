package com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig;

import java.time.LocalDate;
import java.util.concurrent.ConcurrentHashMap;

public class PossibleBuildingClauses {
    private static final ConcurrentHashMap<String, Class<?>> fieldMap = new ConcurrentHashMap<>();

    static {
        fieldMap.put("totalFloors", Integer.class);
        fieldMap.put("undergroundFloors", Integer.class);
        fieldMap.put("overgroundFloors", Integer.class);
        fieldMap.put("GFA", Double.class);
        fieldMap.put("buildingMaterial",Integer.class);
        fieldMap.put("length",Double.class);
        fieldMap.put("dateOfApproval", LocalDate.class);
        fieldMap.put("buildingHumanCapacity",Integer.class);
        fieldMap.put("extraFacility", String.class);
        fieldMap.put("buildingClassification",Integer.class);
        fieldMap.put("buildingSpecification", Integer.class);
    }

    public static Class<?> getBuildingLawClassToken(String field) {
        return fieldMap.get(field);
    }

}
