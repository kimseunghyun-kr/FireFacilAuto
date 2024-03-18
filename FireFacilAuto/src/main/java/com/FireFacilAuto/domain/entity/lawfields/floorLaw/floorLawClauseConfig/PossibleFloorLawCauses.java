package com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;


@Getter
public enum PossibleFloorLawCauses implements PossibleClauses {
    FLOOR_CLASSIFICATION(ClauseValue.INTEGER, "floorClassification", IntegerClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    FLOOR_SPECIFICATION(ClauseValue.INTEGER, "floorSpecification", IntegerClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    FLOOR_NO(ClauseValue.INTEGER, "floorNo", IntegerClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    IS_UNDERGROUND(ClauseValue.BOOLEAN, "isUnderGround", BooleanClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    FLOOR_AREA_SUM(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class, EvaluationType.EVALUATEONREMAININGFIELDS),
    FLOOR_AREA_THRESHOLD(ClauseValue.DOUBLE, "floorArea", DoubleClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    FLOOR_MATERIAL(ClauseValue.INTEGER, "floorMaterial", IntegerClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD),
    FLOOR_WINDOW_AVAILABILITY(ClauseValue.BOOLEAN, "floorWindowAvailability", BooleanClauseValueWrapper.class, EvaluationType.EVAULATEONSINGLEFIELD);

    private final ClauseValue fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper> wrapperClass;
    private final EvaluationType evaluationType;

    PossibleFloorLawCauses(ClauseValue fieldType, String targetField, Class<? extends ClauseValueWrapper> wrapperClass, EvaluationType evaluationType) {
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
        return ClauseTypes.PossibleFloorClauses;
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
