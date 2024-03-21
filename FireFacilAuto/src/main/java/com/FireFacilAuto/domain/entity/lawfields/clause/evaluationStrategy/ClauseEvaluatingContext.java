package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ClauseEvaluatingContext {

    private EvaluationStrategy evaluationStrategy;

    public ClauseEvaluatingContext() {
        this.evaluationStrategy = new SentinelEvaluationStrategy();
    }

    public Boolean evaluate(Clause clause, Field... fields) {
        return evaluationStrategy.evaluate(clause, fields);
    }
}
