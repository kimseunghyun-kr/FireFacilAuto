package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.law.FloorLawForms;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FloorLawFormsToFloorLawFieldsConverter implements Converter<FloorLawForms, FloorLawFields> {

    @Override
    public FloorLawFields convert(FloorLawForms forms) {
        FloorLawFields entity = new FloorLawFields();
        // Map fields from dto to entity
        forms.setMajorCategoryCode(entity.getMajorCategoryCode());
        forms.setMinorCategoryCode(entity.getMinorCategoryCode());
        forms.setFloorClassification(entity.getFloorClassification());
        forms.setFloorSpecification(entity.getFloorSpecification());
        forms.setClauses(entity.getClauses());
        return entity;
    }
}
