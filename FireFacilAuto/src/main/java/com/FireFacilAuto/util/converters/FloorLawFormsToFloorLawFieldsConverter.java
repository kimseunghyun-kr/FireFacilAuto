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
        return target;
    }
}
