package com.FireFacilAuto.domain.entity.floors;

import com.FireFacilAuto.domain.entity.building.field.Field;

public class FloorUtils {
    public static Field getFloorFieldByName(Floor floor, String name) {
        return floor.getFloorFieldMap().get(name);
    }

    public <T> T getFloorFieldValueByName(Floor floor, String name) {
        Field field = getFloorFieldByName(floor, name);
        if (field != null) {
            return (T) field.getValue();
        } else {
            throw new IllegalArgumentException("Field not found: " + name);
        }
    }

    public static Integer getFloorClassification(Floor floor) {
        return (Integer) floor.getFloorFieldMap().get("floorClassification").getValue();
    }

    public static Integer getFloorSpecification(Floor floor) {
        return (Integer) floor.getFloorFieldMap().get("floorSpecification").getValue();
    }
}