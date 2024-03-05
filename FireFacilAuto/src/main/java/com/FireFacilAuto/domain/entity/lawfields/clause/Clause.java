package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

@JsonDeserialize(using = ClauseDeserializer.class)
@Entity
@Data
@Slf4j
public class Clause<T>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    PossibleClauses clauseField;
    ClauseTypes clauseTypes;
    ComparisonOperator comparisonOperator;
    @OneToOne(cascade = CascadeType.ALL)
//    @Convert(converter = ClauseValueConverter.class, attributeName = "value")
    ClauseValueWrapper<T> value;
    int priority;


    public Clause (){
    }

    protected Clause (PossibleClauses clauseField, ClauseTypes clauseTypes, ComparisonOperator co, ClauseValueWrapper<T> value, int priority) {
        this.clauseField = clauseField;
        this.clauseTypes = clauseTypes;
        this.comparisonOperator = co;
        this.value=value;
        this.priority=priority;
    }

    // Get the valueType from the stored class name
    public Class<?> getToken() {
        return clauseField.getFieldType();
    }

    public T getValue() {
        return value.getValue();
    }

}
