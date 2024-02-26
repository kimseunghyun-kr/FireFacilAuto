package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.building.Building;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        log.info("source building at {}", source);
        buildTarget.juso = source.juso;
        buildTarget.GFA = source.GFA;
        buildTarget.totalFloors = source.floor;
        buildTarget.overgroundFloors = source.overgroundFloors;
        buildTarget.undergroundFloors = source.undergroundFloors;
        buildTarget.buildingClassification = source.classification;
        buildTarget.dateofApproval = source.dateOfApproval;
        return buildTarget;
    }
}
