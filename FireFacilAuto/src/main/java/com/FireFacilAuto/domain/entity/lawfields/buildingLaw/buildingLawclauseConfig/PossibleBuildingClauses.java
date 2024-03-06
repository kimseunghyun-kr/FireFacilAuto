package com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;

@Getter
public enum PossibleBuildingClauses implements PossibleClauses {
    TOTAL_FLOORS(ClauseValue.INTEGER, "totalFloors", IntegerClauseValueWrapper.class),
    UNDERGROUND_FLOORS(ClauseValue.INTEGER, "undergroundFloors", IntegerClauseValueWrapper.class),
    OVERGROUND_FLOORS(ClauseValue.INTEGER, "overgroundFloors", IntegerClauseValueWrapper.class),
    GFA(ClauseValue.DOUBLE, "GFA", DoubleClauseValueWrapper.class),
    BUILDING_MATERIAL(ClauseValue.INTEGER, "buildingMaterial", IntegerClauseValueWrapper.class),
    LENGTH(ClauseValue.DOUBLE, "length", DoubleClauseValueWrapper.class),
    DATE_OF_APPROVAL(ClauseValue.LOCAL_DATE, "dateOfApproval", LocalDateClauseValueWrapper.class),
    BUILDING_HUMAN_CAPACITY(ClauseValue.INTEGER, "buildingHumanCapacity", IntegerClauseValueWrapper.class),
    EXTRA_FACILITY(ClauseValue.STRING, "extraFacility", StringClauseValueWrapper.class),
    BUILDING_CLASSIFICATION(ClauseValue.INTEGER, "buildingClassification", IntegerClauseValueWrapper.class),
    BUILDING_SPECIFICATION(ClauseValue.INTEGER, "buildingSpecification", IntegerClauseValueWrapper.class);

    private final ClauseValue fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper> wrapperClass;
    PossibleBuildingClauses(ClauseValue fieldType, String targetField, Class<? extends ClauseValueWrapper> wrapperClass) {
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
        return ClauseTypes.PossibleBuildingClauses;
    }

    @Override
    public Class<? extends ClauseValueWrapper> getWrapper() {
        return this.wrapperClass;
    }
}
