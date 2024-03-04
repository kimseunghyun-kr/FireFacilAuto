package com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleLawField;
import lombok.Getter;


@Getter
public enum PossibleFloorLawCauses implements PossibleLawField {
    FLOOR_CLASSIFICATION(Integer.class, "floorClassification"),
    FLOOR_SPECIFICATION(Integer.class, "floorSpecification"),
    FLOOR_NO(Integer.class, "floorNo"),
    IS_UNDERGROUND(Boolean.class, "isUnderGround"),
    FLOOR_AREA_SUM(Double.class, "floorArea"),
    FLOOR_AREA_THRESHOLD(Double.class, "floorArea"),
    FLOOR_MATERIAL(Integer.class, "floorMaterial"),
    FLOOR_WINDOW_AVAILABILITY(Boolean.class, "floorWindowAvailability");

    private final Class<?> fieldType;
    private final String targetField;

    PossibleFloorLawCauses(Class<?> fieldType, String targetField) {
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
