package com.FireFacilAuto.util;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FormUtilityMethods {

    public boolean buildingFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("totalFloors", "undergroundFloors", "overgroundFloors",
                "GFA", "length", "dateofApproval", "buildingHumanCapacity");
        return fieldsWithConditions.contains(fieldName);
    }

    public List<String> allBuildingFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "totalFloors",
                "undergroundFloors", "overgroundFloors", "GFA", "buildingClassification", "buildingSpecification",
                "length", "dateofApproval", "buildingHumanCapacity");
    }

    public boolean floorFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("floorNo",
                "isUnderGround", "floorArea", "floorMaterial", "floorWindowAvailability"
        );
        return fieldsWithConditions.contains(fieldName);
    }

    public List<String> allFloorFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "floorNo",
                "isUnderGround", "floorClassification", "floorSpecification", "floorArea", "floorMaterial",
                "floorWindowAvailability"
        );
    }

}
