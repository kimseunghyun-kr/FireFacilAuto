package com.FireFacilAuto.domain.DTO.law;

import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingLawForms {


    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public Integer totalFloors; //건물 내 총 층수

    public Integer undergroundFloors; //건물 내 총 지하층

    public Integer overgroundFloors; //건물 내 총 지상층

    public Long GFA; //연면적

    public List<Integer> buildingClassification; //건물 주용도

    public List<Integer> buildingSpecification; //건물 세부용도

    public Long length; //터널 등 지하구 거리

    public LocalDateTime dateofApproval; //사용승인일


//    아직 미포함 정보
    public Integer buildingHumanCapacity; //수용량


    // "조건" 식 저장
    private Map<String, ComparisonOperator> conditions = new HashMap<>();


}
