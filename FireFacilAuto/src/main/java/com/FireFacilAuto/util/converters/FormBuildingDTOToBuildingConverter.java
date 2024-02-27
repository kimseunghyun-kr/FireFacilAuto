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

@Slf4j
public class FormBuildingDTOToBuildingConverter implements Converter<FormBuildingDTO, Building> {
    @Override
    public Building convert(FormBuildingDTO source) {
        Building buildTarget = new Building();
        log.info("source building at {}", source);
        Address juso = source.juso;
        Field<Double> GFA = new Field<>("GFA", source.GFA);
        Field<Integer> totalFloors = new Field<>("totalFloors", source.floor);
        Field<Integer> overgroundFloors = new Field<>("overgroundFloors", source.overgroundFloors);
        Field<Integer> undergroundFloors = new Field<>("undergroundFloors", source.undergroundFloors);
        Field<Integer> classification = new Field<>("classification", source.classification);
        Field<Integer> specification = new Field<>("specification", source.specification);
        Field<LocalDate> dateOfApproval = new Field<>("dateOfApproval", source.dateOfApproval);

        List<Field<?>> template = List.of(GFA, totalFloors, overgroundFloors, undergroundFloors, classification, specification, dateOfApproval);

        buildTarget.setBuildingFieldList(template);
        return buildTarget;
    }
}
