package com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;

@Getter
public enum PossibleBuildingClauses implements PossibleClauses {
    TOTAL_FLOORS(ClauseValue.INTEGER, "totalFloors", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    UNDERGROUND_FLOORS(ClauseValue.INTEGER, "undergroundFloors", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    OVERGROUND_FLOORS(ClauseValue.INTEGER, "overgroundFloors", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    GFA(ClauseValue.DOUBLE, "GFA", DoubleClauseValueWrapper.class, EvaluationType.SINGLE),
    BUILDING_MATERIAL(ClauseValue.INTEGER, "buildingMaterial", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    LENGTH(ClauseValue.DOUBLE, "length", DoubleClauseValueWrapper.class, EvaluationType.SINGLE),
    DATE_OF_APPROVAL(ClauseValue.LOCAL_DATE, "dateOfApproval", LocalDateClauseValueWrapper.class, EvaluationType.SINGLE),
    BUILDING_HUMAN_CAPACITY(ClauseValue.INTEGER, "buildingHumanCapacity", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    EXTRA_FACILITY(ClauseValue.STRING, "extraFacility", StringClauseValueWrapper.class, EvaluationType.STRING_CONCATENATED),
    BUILDING_CLASSIFICATION(ClauseValue.INTEGER, "buildingClassification", IntegerClauseValueWrapper.class, EvaluationType.SINGLE),
    BUILDING_SPECIFICATION(ClauseValue.INTEGER, "buildingSpecification", IntegerClauseValueWrapper.class, EvaluationType.SINGLE);

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
        return ClauseTypes.BuildingClauses;
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
