package com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.PossibleClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum PossibleBuildingClauses implements PossibleClauses {
    TOTAL_FLOORS(Integer.class, "totalFloors", IntegerClauseValueWrapper.class),
    UNDERGROUND_FLOORS(Integer.class, "undergroundFloors", IntegerClauseValueWrapper.class),
    OVERGROUND_FLOORS(Integer.class, "overgroundFloors", IntegerClauseValueWrapper.class),
    GFA(Double.class, "GFA", DoubleClauseValueWrapper.class),
    BUILDING_MATERIAL(Integer.class, "buildingMaterial", IntegerClauseValueWrapper.class),
    LENGTH(Double.class, "length", DoubleClauseValueWrapper.class),
    DATE_OF_APPROVAL(LocalDate.class, "dateOfApproval", LocalDateClauseValueWrapper.class),
    BUILDING_HUMAN_CAPACITY(Integer.class, "buildingHumanCapacity", IntegerClauseValueWrapper.class),
    EXTRA_FACILITY(String.class, "extraFacility", StringClauseValueWrapper.class),
    BUILDING_CLASSIFICATION(Integer.class, "buildingClassification", IntegerClauseValueWrapper.class),
    BUILDING_SPECIFICATION(Integer.class, "buildingSpecification", IntegerClauseValueWrapper.class);

    private final Class<?> fieldType;
    private final String targetField;
    private final Class<? extends ClauseValueWrapper<?>> wrapperClass;
    PossibleBuildingClauses(Class<?> fieldType, String targetField, Class<? extends ClauseValueWrapper<?>> wrapperClass) {
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
    public Class<? extends ClauseValueWrapper<?>> getWrapper() {
        return this.wrapperClass;
    }
}
