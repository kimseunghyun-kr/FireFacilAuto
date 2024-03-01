package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig.PossibleBuildingClauses;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;


@Embeddable
@Data
@Slf4j
public class Clause<T>{

    String fieldname;
    ComparisonOperator comparisonOperator;
    T value;
    int priority;
    String valueType;

    public Clause (){
    }

    private Clause(String fieldname, ComparisonOperator co, T input, int priority, String valueType) {
        this.fieldname = fieldname;
        this.comparisonOperator = co;
        this.value=input;
        this.priority=priority;
        this.valueType = valueType;
    }

    // Set the valueTypeClassName when setting valueType
    // Set the valueType when setting value
    private void setValue(T value) {
        this.value = value;
        this.valueType = value.getClass().getName();
    }

    // Get the valueType from the stored class name
    public Class<?> getToken() {
        return PossibleBuildingClauses.getBuildingLawClassToken(fieldname);
    }


    public static <T> Clause<T> clauseFactory(String fieldname, ComparisonOperator co, T input, int priority) {
        return new Clause<>(fieldname,co,input,priority, PossibleBuildingClauses.getBuildingLawClassToken(fieldname).getSimpleName());
    }


}
