package com.FireFacilAuto.domain.DTO.law;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class BuildingLawForms implements Serializable {

    @Positive
    private Integer majorCategoryCode;

    @Positive
    private Integer minorCategoryCode;

    @Min(value = -1, message = "Value must be at least -1")
    private Integer buildingClassification;

    @Min(value = -1, message = "Value must be at least -1")
    private Integer buildingSpecification;

//    @Positive
//    private Integer ClauseDepth;

    private List<Clause<?>> clauses;

    // Additional constructors or methods as needed


    public static List<String> allBuildingFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "totalFloors",
                "undergroundFloors", "overgroundFloors", "GFA", "buildingPurpose",
                "length", "dateofApproval", "buildingHumanCapacity");
    }

    public static boolean buildingFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("totalFloors", "undergroundFloors", "overgroundFloors",
                "GFA", "length", "dateofApproval", "buildingHumanCapacity");
        return fieldsWithConditions.contains(fieldName);
    }


}
