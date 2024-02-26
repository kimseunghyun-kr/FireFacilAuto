package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormFloorDTO;
import com.FireFacilAuto.domain.entity.building.Floor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class FormFloorDTOToFloorConverter implements Converter<FormFloorDTO, Floor> {
    @Override
    public Floor convert(FormFloorDTO source) {
        Floor floor = new Floor();
        log.info("converting {}", source);
        floor.setFloorNo(source.getFloorNo());
        floor.setFloorClassification(source.getFloorClassification());
        floor.setFloorSpecification(source.getFloorSpecification());
        floor.setFloorArea(source.getFloorArea());
        floor.setFloorWindowAvailability(source.getFloorWindowAvailability());
        floor.setIsUnderGround(source.getIsUnderground());
        floor.setFloorMaterial(source.getFloorMaterial());
        return floor;
    }
}
