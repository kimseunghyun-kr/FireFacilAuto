package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.core.convert.converter.Converter;

public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        buildTarget.juso = source.juso;
        buildTarget.GFA = source.GFA;
        buildTarget.totalFloors = source.floor;
        buildTarget.buildingClassification = source.classification;
        buildTarget.dateofApproval = source.dateOfApproval;
        return buildTarget;
    }
}
