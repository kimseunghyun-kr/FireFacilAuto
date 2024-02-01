package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.DTO.law.BuildingLawForms;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BuildingLawFormsToBuildingLawFieldsConverter implements Converter<BuildingLawForms, List<BuildingLawFields>> {

    @Override
    public List<BuildingLawFields> convert(BuildingLawForms source) {
        BuildingLawFields target = new BuildingLawFields();

        target.setMajorCategoryCode(source.getMajorCategoryCode());
        target.setMinorCategoryCode(source.getMinorCategoryCode());
        target.setTotalFloors(source.getTotalFloors());
        target.setUndergroundFloors(source.getUndergroundFloors());
        target.setOvergroundFloors(source.getOvergroundFloors());
        target.setGFA(source.getGFA());

        // Convert List<Integer> to Integer if needed
//        if (source.getBuildingClassification() != null && !source.getBuildingClassification().isEmpty()) {
//            target.setBuildingClassification(source.getBuildingClassification().get(0));
//        }
//
//        if (source.getBuildingSpecification() != null && !source.getBuildingSpecification().isEmpty()) {
//            target.setBuildingSpecification(source.getBuildingSpecification().get(0));
//        }

        target.setLength(source.getLength());
        target.setDateofApproval(source.getDateofApproval());
        target.setBuildingHumanCapacity(source.getBuildingHumanCapacity());

        return Arrays.asList(target);
    }
}
