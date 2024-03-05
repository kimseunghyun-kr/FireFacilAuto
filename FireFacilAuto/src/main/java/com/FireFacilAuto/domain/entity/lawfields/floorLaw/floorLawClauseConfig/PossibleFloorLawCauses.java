package com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.BooleanClauseValueWrapper;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.DoubleClauseValueWrapper;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.IntegerClauseValueWrapper;
import lombok.Getter;


@Getter
public enum PossibleFloorLawCauses implements PossibleClauses {
    FLOOR_CLASSIFICATION(Integer.class, "floorClassification", IntegerClauseValueWrapper.class),
    FLOOR_SPECIFICATION(Integer.class, "floorSpecification", IntegerClauseValueWrapper.class),
    FLOOR_NO(Integer.class, "floorNo", IntegerClauseValueWrapper.class),
    IS_UNDERGROUND(Boolean.class, "isUnderGround", BooleanClauseValueWrapper.class),
    FLOOR_AREA_SUM(Double.class, "floorArea", DoubleClauseValueWrapper.class),
    FLOOR_AREA_THRESHOLD(Double.class, "floorArea", DoubleClauseValueWrapper.class),
    FLOOR_MATERIAL(Integer.class, "floorMaterial", IntegerClauseValueWrapper.class),
    FLOOR_WINDOW_AVAILABILITY(Boolean.class, "floorWindowAvailability", BooleanClauseValueWrapper.class);

    private final Class<?> fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper<?>> wrapperClass;

    PossibleFloorLawCauses(Class<?> fieldType, String targetField, Class<? extends ClauseValueWrapper<?>> wrapperClass) {
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
    public Class<? extends ClauseValueWrapper<?>> getWrapper() {
        return this.wrapperClass;
    }
}
