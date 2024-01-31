package com.FireFacilAuto.domain.lawExecution;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;

public abstract class Law {
    public Boolean isApplicabletoWholeBuilding; //건물 내 모든 층 적용

    public Boolean isApplicabletoAllFloorsWithSameClassification; //건물 내 동일 용도 층 전체 적용

    public Integer ClassificationtoApply; //타깃 주용도

    public Integer SpecificationtoApply; //타깃 세부용도

    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public abstract Boolean evaluateBuilding(Building building);

    public abstract Boolean evaluateFloor(Floor floor);



}
