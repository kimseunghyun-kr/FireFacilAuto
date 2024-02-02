package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FloorLawFormsToFloorLawFieldsConverter implements Converter<FloorLawForms, FloorLawFields> {

    @Override
    public FloorLawFields convert(FloorLawForms source) {
        FloorLawFields target = new FloorLawFields();

        target.setMajorCategoryCode(source.getMajorCategoryCode());
        target.setMinorCategoryCode(source.getMinorCategoryCode());
        target.setFloorNo(source.getFloorNo());
        target.setIsUnderGround(source.getIsUnderGround());
        target.setFloorClassification(-1); // Set a default value, adjust as needed
        target.setFloorSpecification(-1); // Set a default value, adjust as needed
        target.setFloorArea(source.getFloorArea());
        target.setFloorMaterial(source.getFloorMaterial());

        // Set default values for fields not present in FloorLawForms
        target.setFloorWindowAvailability(false); // Set a default value, adjust as needed

        return target;
    }
}
