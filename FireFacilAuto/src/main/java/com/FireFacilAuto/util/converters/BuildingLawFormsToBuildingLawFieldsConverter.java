package com.FireFacilAuto.util.converters;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuildingLawFormsToBuildingLawFieldsConverter implements Converter<BuildingLawForms, BuildingLawFields> {

    @Override
    public BuildingLawFields convert(BuildingLawForms forms) {
        BuildingLawFields entity = new BuildingLawFields();
        // Map fields from dto to entity
        forms.setMajorCategoryCode(entity.getMajorCategoryCode());
        forms.setMinorCategoryCode(entity.getMinorCategoryCode());
        forms.setBuildingClassification(entity.getBuildingClassification());
        forms.setBuildingSpecification(entity.getBuildingSpecification());
        forms.setClauses(entity.getClauses());
        return entity;
    }

}
