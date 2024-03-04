package com.FireFacilAuto.domain.entity.lawfields.clause;

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

    protected Clause (PossibleLawField lawField, ComparisonOperator co, T input, int priority, String valueType) {

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


}
