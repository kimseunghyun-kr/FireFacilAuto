package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparisonStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClauseEvaluator<T> {
    private final ComparisonStrategy<T> comparisonStrategy;

    public ClauseEvaluator(ComparisonStrategy<T> comparisonStrategy) {
        this.comparisonStrategy = comparisonStrategy;
    }

    public Boolean evaluate(Clause<T> clause, T input) {
        log.info("Operand 1: {} {} Operand 2: {}", input, clause.getComparisonOperator(), clause.getValue());
        return comparisonStrategy.compare(input, clause.getValue(), clause.getComparisonOperator());
    }
}
