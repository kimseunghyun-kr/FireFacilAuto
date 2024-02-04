package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.api.ApiResponseItem;
import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseItemToBuildingConverter implements Converter<ApiResponseItem, Building> {
    @Override
    public Building convert(ApiResponseItem source) {
        Building building = new Building();
        building.setGFA(33.0);
//        building.setBuildingClassification();
//        building.setBuildingSpecification();
//        building.setLength();
//        building.setBuildingHumanCapacity();
//        building.setTotalFloors();
//        building.setOvergroundFloors();
//        building.setUndergroundFloors();
//        building.setDateofApproval();
//        building.setCompositeFloors();
        return building;
    }

}
