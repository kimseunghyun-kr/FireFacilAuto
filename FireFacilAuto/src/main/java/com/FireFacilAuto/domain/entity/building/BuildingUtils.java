package com.FireFacilAuto.domain.entity.building;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BuildingUtils {
    public static Field<?> getBuildingFieldByName(Building building, String name) {
        return building.getBuildingFieldMap().get(name);
    }

    public static <T> T getBuildingFieldValueByName(Building building, String name) {
        Field<?> field = getBuildingFieldByName(building,name);
        if (field != null) {
            return (T) field.value();
        } else {
            throw new IllegalArgumentException("Field not found: " + name);
        }
    }

    public static Integer getBuildingClassification(Building building) {
        return (Integer)building.getBuildingFieldMap().get("buildingClassification").value();
    }
    public static Integer getBuildingSpecification(Building building) {
        return (Integer)building.getBuildingFieldMap().get("buildingSpecification").value();
    }


}