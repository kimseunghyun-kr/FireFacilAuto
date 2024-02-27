package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuildingLawFormsToBuildingLawFieldsConverter implements Converter<BuildingLawForms, BuildingLawFields> {

    @Override
    public BuildingLawFields convert(BuildingLawForms source) {
        BuildingLawFields target = new BuildingLawFields();

        target.setMajorCategoryCode(source.getMajorCategoryCode());
        target.setMinorCategoryCode(source.getMinorCategoryCode());


        return target;
    }

}
