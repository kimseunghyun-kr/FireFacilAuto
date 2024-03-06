package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.building.field.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

@Slf4j
public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        log.info("source building at {}", source);
        buildTarget.setJuso(source.juso);

        Map<String,Field> fields = BuildingAttributes.builder()
                .buildingClassification(source.classification)
                .buildingSpecification(source.specification)
                .gfa(source.GFA)
                .approvalDate(source.dateOfApproval)
                .buildingMaterial(source.buildingMaterial)
                .overgroundFloors(source.overgroundFloors)
                .undergroundFloors(source.undergroundFloors)
                .buildFields();


        buildTarget.setBuildingFieldMap(fields);
        return buildTarget;
    }
}
