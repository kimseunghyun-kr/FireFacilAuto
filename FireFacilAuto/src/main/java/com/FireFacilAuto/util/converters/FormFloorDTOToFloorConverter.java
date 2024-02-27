package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormFloorDTO;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;

@Slf4j
public class FormFloorDTOToFloorConverter implements Converter<FormFloorDTO, Floor> {
    @Override
    public Floor convert(FormFloorDTO source) {
        Floor floor = new Floor();
        log.info("converting {}", source);
        Field<Integer> floorNo = new Field<>("floorNo", source.getFloorNo(), getFloorClass("floorNo"));
        Field<Integer> floorClassification = new Field<>("floorClassifcation", source.getFloorClassification(), getFloorClass("floorClassification"));
        Field<Integer> floorSpecification = new Field<>("floorSpecification", source.getFloorSpecification(), getFloorClass("floorSpecification"));
        Field<Double> floorArea = new Field<>("floorArea", source.getFloorArea(), getFloorClass("floorArea"));
        Field<Boolean> floorWindowAvailability = new Field<>("floorWindowAvailability", source.getFloorWindowAvailability(), getFloorClass("floorWindowAvailability"));
        Field<Boolean> isUnderGround = new Field<>("isUnderGround", source.getIsUnderground(), getFloorClass("isUnderGround"));
        Field<Integer> floorMaterial = new Field<>("floorMaterial", source.getFloorMaterial(), getFloorClass("floorMaterial"));

        List<Field<?>> fields = List.of(floorNo, floorClassification, floorSpecification, floorArea, floorWindowAvailability, isUnderGround, floorMaterial);
        floor.setFloorFieldList(fields);

        return floor;
    }
}
