package com.FireFacilAuto.util.form;

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
                "totalUndergroundFloors", "totalOvergroundFloors", "GFA", "buildingPurpose",
                "length", "dateofApproval", "buildingHumanCapacity");
    }

    public boolean floorFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("floorNo",
                "floorArea", "floorWindowAvailability", "floorAreaThreshold"
        );
        return fieldsWithConditions.contains(fieldName);
    }

    public List<String> allFloorFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "floorNo",
                "isUnderGround", "floorPurpose" , "floorArea", "floorMaterial",
                "floorWindowAvailability", "floorAreaThreshold"
        );
    }

}
