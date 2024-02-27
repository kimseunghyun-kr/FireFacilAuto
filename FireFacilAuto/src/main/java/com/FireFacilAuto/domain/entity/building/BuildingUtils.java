package com.FireFacilAuto.domain.entity.building;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BuildingUtils {
    public static Field<?> getBuildingFieldByName(Building building, String name) {
        return building.getBuildingFieldList().stream().filter(field -> field.fieldName().equals(name)).findFirst().orElseThrow();
    }

    public static Integer getBuildingClassification(Building building) {
        return (Integer)building.getBuildingFieldList().stream().filter(field -> field.fieldName().equals("buildingClassification")).findFirst().orElseThrow().value();
    }
    public static Integer getBuildingSpecification(Building building) {
        return (Integer)building.getBuildingFieldList().stream().filter(field -> field.fieldName().equals("buildingSpecification")).findFirst().orElseThrow().value();
    }


}
