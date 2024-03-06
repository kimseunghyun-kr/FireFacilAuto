package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Component;

@Component
public class ClauseFactory {


    public <T extends ClauseValueWrapper> Clause<T> createClauseWithClauseValueWrapper(String field, ClauseTypes lawtype, ComparisonOperator co, T input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        return new Clause<>(lawField, lawtype, co, input, priority);
    }

    public <T> Clause<? extends ClauseValueWrapper> createClause(String field, ClauseTypes lawtype, ComparisonOperator co, T input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        ClauseValueWrapper valueWrapper = ClauseValueWrapper.clauseValueWrapperfactory(lawField.getFieldType(), input);
        Class<? extends ClauseValueWrapper> castingClass = lawField.getWrapper();
        return new Clause<>(lawField, lawtype, co, castingClass.cast(valueWrapper), priority);
    }

    public Clause<ClauseValueWrapper> createAbstractWrapperClause (String field, ClauseTypes lawtype, ComparisonOperator co, Object input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        ClauseValueWrapper valueWrapper = ClauseValueWrapper.clauseValueWrapperfactory(lawField.getFieldType(), input);
        return new Clause<>(lawField, lawtype, co, valueWrapper, priority);
    }
}
