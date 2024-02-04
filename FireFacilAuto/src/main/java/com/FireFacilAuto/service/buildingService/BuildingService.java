package com.FireFacilAuto.service.buildingService;

import com.FireFacilAuto.domain.DTO.api.ApiResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildingService {

    private final ConversionService conversionService;

    @Autowired
    public BuildingService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    //    convert to building to execute later
    public List<Building> process(List<ApiResponseItem> resultList, Address address) {
        List<Building> result = resultList.stream().map(
                apiResponseItem -> mapper(apiResponseItem, address)
        ).collect(Collectors.toList());
        return null;
    }

    private Building mapper(@NotNull ApiResponseItem apiResponseItem, Address address) {
        Building building = conversionService.convert(apiResponseItem, Building.class);
        assert building != null;
        building.setJuso(address);
        return building;
    }
}
