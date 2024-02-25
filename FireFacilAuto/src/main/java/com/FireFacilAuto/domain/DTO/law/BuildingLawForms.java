package com.FireFacilAuto.domain.DTO.law;

import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BuildingLawForms {


    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public Integer totalFloors; //건물 내 총 층수

    public Integer buildingMaterial;

    public Integer undergroundFloors; //건물 내 총 지하층

    public Integer overgroundFloors; //건물 내 총 지상층

    public Double GFA; //연면적

    public String buildingPurpose; //건물 용도

    public Double length; //터널 등 지하구 거리

    public LocalDate dateofApproval; //사용승인일


//    아직 미포함 정보
    public Integer buildingHumanCapacity; //수용량


    // "조건" 식 저장
    private Map<String, ComparisonOperator> conditions = new HashMap<>();

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
