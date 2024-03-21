package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparableComparisonStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;
import static com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator.aggregate;

@Slf4j
public class EvaluateByAggregateFieldStrategy<T extends Number & Comparable<T>> implements EvaluationStrategy{

    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        String targetAggregationField = clause.getClauseField().getLawFieldName();
        Class<?> lawFieldValueToken = clause.getToken();
        Class<?> clazz = getFloorClass(targetAggregationField);

        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                targetAggregationField, clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());

        if(!lawFieldValueToken.equals(clazz)) {
            throw new UnsupportedOperationException("non matching token types of target floor Field Types in aggregation");
        }

        List<Field> aggregatedFloorFields = List.of(fields);

        Object lawValue = clause.getValue();
        T numericLawValue = (T) lawValue;
        T result = aggregate(aggregatedFloorFields);

        ComparableComparisonStrategy<T> strategy = new ComparableComparisonStrategy<>();
        return strategy.compare(aggregate(aggregatedFloorFields), numericLawValue, clause.getComparisonOperator());
    }
}
