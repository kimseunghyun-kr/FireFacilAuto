package com.FireFacilAuto.util.converters;


import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseItemToBuildingConverter implements Converter<BaseResponseItem, Building> {
    @Override
    public Building convert(BaseResponseItem source) {
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
