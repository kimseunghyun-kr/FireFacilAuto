package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;

public class SentinelEvaluationStrategy implements EvaluationStrategy{
    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        throw new UnsupportedOperationException("this is an unsupported evaluation method, " +
                "this can potentially be due to a the implementation enums of PossibleClauses not" +
                "having an appropriate EvaluationStrategy being assigned" +
                "or random bugs. but definitely related to the EvaluationStrategies. good luck hunting");
    }
}
