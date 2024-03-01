package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.building.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.FireFacilAuto.domain.entity.building.PossibleBuildingFields.getBuildingClass;

@Slf4j
public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        log.info("source building at {}", source);
        buildTarget.setJuso(source.juso);

        Map<String,Field<?>> fields = BuildingAttributes.builder()
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
