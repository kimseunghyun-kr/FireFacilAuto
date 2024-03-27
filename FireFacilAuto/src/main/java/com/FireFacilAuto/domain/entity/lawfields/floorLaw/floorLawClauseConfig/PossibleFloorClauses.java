package com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;


@Getter
public enum PossibleFloorClauses implements PossibleClauses {
    FLOOR_CLASSIFICATION(ClauseValue.INTEGER, "floorClassification", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    FLOOR_SPECIFICATION(ClauseValue.INTEGER, "floorSpecification", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    FLOOR_NO(ClauseValue.INTEGER, "floorNo", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    IS_UNDERGROUND(ClauseValue.BOOLEAN, "isUnderGround", BooleanClauseValueWrapper.class, EvaluationType.SINGLE),
    FLOOR_AREA_SUM(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class, EvaluationType.AGGREGATE_REMAINING),
    FLOOR_AREA_THRESHOLD(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class, EvaluationType.SINGLE),
    FLOOR_MATERIAL(ClauseValue.INTEGER, "floorMaterial", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    FLOOR_WINDOW_AVAILABILITY(ClauseValue.BOOLEAN, "floorWindowAvailability", BooleanClauseValueWrapper.class, EvaluationType.SINGLE);

    private final ClauseValue fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper> wrapperClass;
    private final EvaluationType evaluationType;

    PossibleFloorClauses(ClauseValue fieldType, String targetField, Class<? extends ClauseValueWrapper> wrapperClass, EvaluationType evaluationType) {
        this.fieldType = fieldType;
        this.targetField = targetField;
        this.wrapperClass = wrapperClass;
        this.evaluationType = evaluationType;
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
        return ClauseTypes.FloorClauses;
    }

    @Override
    public EvaluationType getEvaluationType() {
        return this.evaluationType;
    }

    @Override
    public Class<? extends ClauseValueWrapper> getWrapper() {
        return this.wrapperClass;
    }
}
