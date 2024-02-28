package com.FireFacilAuto.domain.DTO.law;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FloorLawForms {

    @Positive
    private Integer majorCategoryCode;

    @Positive
    private Integer minorCategoryCode;

    @Min(value = -1, message = "Value must be at least -1")
    private Integer floorClassification;

    @Min(value = -1, message = "Value must be at least -1")
    private Integer floorSpecification;

//    @Positive
//    private Integer ClauseDepth;

    private List<Clause<?>> clauses;

    public static FloorLawForms fromEntity(FloorLawFields entity) {
        FloorLawForms dto = new FloorLawForms();
        // Map fields from entity to dto
        dto.setMajorCategoryCode(entity.getMajorCategoryCode());
        dto.setMinorCategoryCode(entity.getMinorCategoryCode());
        dto.setFloorClassification(entity.getFloorClassification());
        dto.setFloorSpecification(entity.getFloorSpecification());
        dto.setClauses(entity.getClauses());
        return dto;
    }

    public static List<String> allFloorFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "floorNo",
                "isUnderGround", "floorPurpose" , "floorAreaSum", "floorAreaThreshold", "floorMaterial",
                "floorWindowAvailability"
        );
    }

    public static boolean floorFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("floorNo",
                "floorAreaSum", "floorAreaThreshold"
        );
        return fieldsWithConditions.contains(fieldName);
    }
}
