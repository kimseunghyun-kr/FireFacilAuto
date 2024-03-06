package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.form.FormFloorDTO;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import java.util.Map;


@Slf4j
public class FormFloorDTOToFloorConverter implements Converter<FormFloorDTO, Floor> {
    @Override
    public Floor convert(FormFloorDTO source) {
        Floor floor = new Floor();
        log.info("converting {}", source);
        Map<String,Field> fields = new FloorAttributes.FloorBuilder()
                .floorClassification(source.getFloorClassification())
                .floorSpecification(source.getFloorSpecification())
                .floorArea(source.getFloorArea())
                .floorMaterial(source.getFloorMaterial())
                .isUnderGround(source.getIsUnderground())
                .floorNo(source.getFloorNo())
                .floorWindowAvailability(source.getFloorWindowAvailability())
                .build().getFloorFieldMap();

        floor.setFloorFieldMap(fields);

        return floor;
    }
}
