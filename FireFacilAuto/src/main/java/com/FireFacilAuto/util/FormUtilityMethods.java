package com.FireFacilAuto.util;

import java.util.Arrays;
import java.util.List;

public class FormUtilityMethods {

    public boolean buildingFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("totalFloors", "undergroundFloors", "overgroundFloors",
                "GFA", "length", "dateofApproval", "buildingHumanCapacity");
        return fieldsWithConditions.contains(fieldName);
    }

    public boolean floorFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("floorNo",
                "isUnderGround", "floorArea", "floorMaterial", "floorWindowAvailability"
        );
        return fieldsWithConditions.contains(fieldName);
    }

}
