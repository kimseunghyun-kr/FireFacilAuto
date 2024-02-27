package com.FireFacilAuto.domain.entity.floors;

import com.FireFacilAuto.domain.entity.building.Field;

public class FloorUtils {
    public static Field<?> getFloorFieldByName(Floor floor, String name) {
        return floor.getFloorFieldList().stream().filter(field -> field.fieldName().equals(name)).findFirst().orElseThrow();
    }
    public static Integer getFloorClassification(Floor floor) {
        return (Integer)floor.getFloorFieldList().stream().filter(field -> field.fieldName().equals("buildingClassification")).findFirst().orElseThrow().value();
    }
    public static Integer getFloorSpecification(Floor floor) {
        return (Integer)floor.getFloorFieldList().stream().filter(field -> field.fieldName().equals("buildingSpecification")).findFirst().orElseThrow().value();
    }


}
