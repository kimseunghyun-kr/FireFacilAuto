package com.FireFacilAuto.domain.DTO.law;

import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FloorLawForms {

    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public Integer floorNo; //충수 조건

    public Boolean isUnderGround; //지하여부

    public String floorPurpose; //층 용도

    public Double floorAreaSum; //층 바닥면적 합

    public Double floorAreaThreshold; //층 바닥면적 임계

    public Integer floorMaterial; //층 재료

//    아직 미포함인 정보

    public Boolean floorWindowAvailability; //무창층

    // "조건" 식 저장
    private Map<String, ComparisonOperator> conditions = new HashMap<>();

    public static List<String> allFloorFields() {
        return Arrays.asList("majorCategoryCode", "minorCategoryCode", "floorNo",
                "isUnderGround", "floorPurpose" , "floorAreaSum", "floorAreaThreshold", "floorMaterial",
                "floorWindowAvailability"
        );
    }

    public static boolean floorFieldAssociableWithCondition(String fieldName) {
        List<String> fieldsWithConditions = Arrays.asList("floorNo",
                "floorAreaSum", "floorWindowAvailability", "floorAreaThreshold"
        );
        return fieldsWithConditions.contains(fieldName);
    }
}
