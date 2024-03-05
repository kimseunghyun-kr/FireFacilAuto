package com.FireFacilAuto.domain.entity.lawfields.clause;

import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Component;

@Component
public class ClauseFactory {


    public <T> Clause<T> createClause(String field, ClauseTypes lawtype, ComparisonOperator co, T input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        ClauseValueWrapper<T> valueWrapper = new ClauseValueWrapper<>(input, lawField.getFieldType().getSimpleName());
        return new Clause<>(lawField, lawtype, co, valueWrapper, priority);
    }
}
