package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Component;

@Component
public class ClauseFactory {

    public Clause createClauseWithDefinedEnumAndValueWrapper (PossibleClauses field, ClauseTypes lawtype, ComparisonOperator co, ClauseValueWrapper input, int priority) {
        if (field == null) {
            throw new IllegalArgumentException("PossibleClauses cannot be null. ");
        }
        return new Clause(field, lawtype, co, input, priority, field.getEvaluationType());
    }

    public Clause createClauseWithClauseValueWrapper(String field, ClauseTypes lawtype, ComparisonOperator co, ClauseValueWrapper input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        return new Clause(lawField, lawtype, co, input, priority, lawField.getEvaluationType());
    }

    public <T> Clause createClause(String field, ClauseTypes lawtype, ComparisonOperator co, T input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        ClauseValueWrapper valueWrapper = ClauseValueWrapper.clauseValueWrapperfactory(lawField.getFieldType(), input);
        Class<? extends ClauseValueWrapper> castingClass = lawField.getWrapper();
        return new Clause(lawField, lawtype, co, castingClass.cast(valueWrapper), priority, lawField.getEvaluationType());
    }

    public Clause createAbstractWrapperClause (String field, ClauseTypes lawtype, ComparisonOperator co, Object input, int priority) {
        PossibleClauses lawField = lawtype.getClauseByName(field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        ClauseValueWrapper valueWrapper = ClauseValueWrapper.clauseValueWrapperfactory(lawField.getFieldType(), input);
        return new Clause(lawField, lawtype, co, valueWrapper, priority, lawField.getEvaluationType());
    }
}
