package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.FireFacilAuto.domain.entity.building.PossibleBuildingFields.getBuildingClass;

@Slf4j
public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        log.info("source building at {}", source);
        buildTarget.setJuso(source.juso);
        Field<Double> GFA = new Field<>("GFA", source.GFA, getBuildingClass("GFA"));
        Field<Integer> totalFloors = new Field<>("totalFloors", source.floor, getBuildingClass("totalFloors"));
        Field<Integer> overgroundFloors = new Field<>("overgroundFloors", source.overgroundFloors, getBuildingClass("overgroundFloors"));
        Field<Integer> undergroundFloors = new Field<>("undergroundFloors", source.undergroundFloors, getBuildingClass("undergroundFloors"));
        Field<Integer> classification = new Field<>("buildingClassification", source.classification, getBuildingClass("buildingClassification"));
        Field<Integer> specification = new Field<>("buildingSpecification", source.specification, getBuildingClass("buildingSpecification"));
        Field<LocalDate> dateOfApproval = new Field<>("dateOfApproval", source.dateOfApproval, getBuildingClass("dateOfApproval"));

        List<Field<?>> template = List.of(GFA, totalFloors, overgroundFloors, undergroundFloors, classification, specification, dateOfApproval);

        buildTarget.setBuildingFieldList(template);
        return buildTarget;
    }
}
