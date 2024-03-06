package com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;


@Getter
public enum PossibleFloorLawCauses implements PossibleClauses {
    FLOOR_CLASSIFICATION(ClauseValue.INTEGER, "floorClassification", IntegerClauseValueWrapper.class),
    FLOOR_SPECIFICATION(ClauseValue.INTEGER, "floorSpecification", IntegerClauseValueWrapper.class),
    FLOOR_NO(ClauseValue.INTEGER, "floorNo", IntegerClauseValueWrapper.class),
    IS_UNDERGROUND(ClauseValue.BOOLEAN, "isUnderGround", BooleanClauseValueWrapper.class),
    FLOOR_AREA_SUM(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class),
    FLOOR_AREA_THRESHOLD(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class),
    FLOOR_MATERIAL(ClauseValue.INTEGER, "floorMaterial", IntegerClauseValueWrapper.class),
    FLOOR_WINDOW_AVAILABILITY(ClauseValue.BOOLEAN, "floorWindowAvailability", BooleanClauseValueWrapper.class);

    private final ClauseValue fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper> wrapperClass;

    PossibleFloorLawCauses(ClauseValue fieldType, String targetField, Class<? extends ClauseValueWrapper> wrapperClass) {
        this.fieldType = fieldType;
        this.targetField = targetField;
        this.wrapperClass = wrapperClass;
    }

    @Override
    public String getLawFieldName() {
        return this.name();
    }

    @Override
    public String getTargetFieldName() {
        return this.targetField;
    }

    @Override
    public ClauseTypes LawType() {
        return ClauseTypes.PossibleFloorClauses;
    }

    @Override
    public Class<? extends ClauseValueWrapper> getWrapper() {
        return this.wrapperClass;
    }
}
