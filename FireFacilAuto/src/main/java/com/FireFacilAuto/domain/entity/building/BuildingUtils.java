package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.building.field.Field;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BuildingUtils {
    public static Field getBuildingFieldByName(Building building, String name) {
        return building.getBuildingFieldMap().get(name);
    }

    public static Object getBuildingFieldValueByName(Building building, String name) {
        Field field = getBuildingFieldByName(building,name);
        if (field != null) {
            Class<?> clazz = field.getValueType();
            return clazz.cast(field.getValue());
        } else {
            throw new IllegalArgumentException("Field not found: " + name);
        }
    }

    public static Integer getBuildingClassification(Building building) {
        return (Integer)building.getBuildingFieldMap().get("buildingClassification").getValue();
    }
    public static Integer getBuildingSpecification(Building building) {
        return (Integer)building.getBuildingFieldMap().get("buildingSpecification").getValue();
    }


}