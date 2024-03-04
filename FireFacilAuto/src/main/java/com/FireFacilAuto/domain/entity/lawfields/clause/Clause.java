package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.floors.PossibleFloorFields;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorLawCauses;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;


@Embeddable
@Data
@Slf4j
public class Clause<T>{

    PossibleLawField lawField;
    ComparisonOperator comparisonOperator;
    T value;
    int priority;
    String valueType;

    public Clause (){
    }

    protected Clause(PossibleLawField lawField, ComparisonOperator co, T input, int priority, String valueType) {

        this.lawField = lawField;
        this.comparisonOperator = co;
        this.value=input;
        this.priority=priority;
        this.valueType = valueType;
    }

    // Get the valueType from the stored class name
    public Class<?> getToken() {
        return lawField.getFieldType();
    }


    // Factory method using enum
//    public static <T> Clause<T> createClause(String field, Class<?> enumType, ComparisonOperator co, T input, int priority) {
//        PossibleLawField lawField;
//        if(enumType.equals(PossibleFloorFields.class)) {
//            lawField = PossibleFloorLawCauses.valueOf(field);
//        } else if (enumType.equals(PossibleBuildingClauses.class)) {
//            lawField = PossibleBuildingClauses.valueOf(field);
//        } else {
//            throw new UnsupportedOperationException("this is an unsupported type of law class you are trying to infer.");
//        }
//        return new Clause<>(lawField, co, input, priority, lawField.getFieldType().getSimpleName());
//    }

}
