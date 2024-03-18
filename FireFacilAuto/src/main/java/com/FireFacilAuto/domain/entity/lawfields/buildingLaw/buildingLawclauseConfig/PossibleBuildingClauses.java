package com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;

@Getter
public enum PossibleBuildingClauses implements PossibleClauses {
    TOTAL_FLOORS(ClauseValue.INTEGER, "totalFloors", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    UNDERGROUND_FLOORS(ClauseValue.INTEGER, "undergroundFloors", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    OVERGROUND_FLOORS(ClauseValue.INTEGER, "overgroundFloors", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    GFA(ClauseValue.DOUBLE, "GFA", DoubleClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    BUILDING_MATERIAL(ClauseValue.INTEGER, "buildingMaterial", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    LENGTH(ClauseValue.DOUBLE, "length", DoubleClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    DATE_OF_APPROVAL(ClauseValue.LOCAL_DATE, "dateOfApproval", LocalDateClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    BUILDING_HUMAN_CAPACITY(ClauseValue.INTEGER, "buildingHumanCapacity", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    EXTRA_FACILITY(ClauseValue.STRING, "extraFacility", StringClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    BUILDING_CLASSIFICATION(ClauseValue.INTEGER, "buildingClassification", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS),
    BUILDING_SPECIFICATION(ClauseValue.INTEGER, "buildingSpecification", IntegerClauseValueWrapper.class, EvaluationType.EVALUATEONALLFIELDS);

    private final ClauseValue fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper> wrapperClass;
    private final EvaluationType evaluationType;
    PossibleBuildingClauses(ClauseValue fieldType,
                            String targetField,
                            Class<? extends ClauseValueWrapper> wrapperClass,
                            EvaluationType evaluationType) {
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
        return ClauseTypes.PossibleBuildingClauses;
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
