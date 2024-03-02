package com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleLawField;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum PossibleBuildingClauses implements PossibleLawField {
    TOTAL_FLOORS(Integer.class, "totalFloors"),
    UNDERGROUND_FLOORS(Integer.class, "undergroundFloors"),
    OVERGROUND_FLOORS(Integer.class, "overgroundFloors"),
    GFA(Double.class, "GFA"),
    BUILDING_MATERIAL(Integer.class, "buildingMaterial"),
    LENGTH(Double.class, "length"),
    DATE_OF_APPROVAL(LocalDate.class, "dateOfApproval"),
    BUILDING_HUMAN_CAPACITY(Integer.class, "buildingHumanCapacity"),
    EXTRA_FACILITY(String.class, "extraFacility"),
    BUILDING_CLASSIFICATION(Integer.class, "buildingClassification"),
    BUILDING_SPECIFICATION(Integer.class, "buildingSpecification");

    private final Class<?> fieldType;
    private final String targetField;
    PossibleBuildingClauses(Class<?> fieldType, String targetField) {
        this.fieldType = fieldType;
        this.targetField = targetField;
    }

    @Override
    public String getLawFieldName() {
        return this.name();
    }

    @Override
    public String getTargetFieldName() {
        return this.targetField;
    }
}
