package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFieldComparatorConfig;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparableComparisonStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorFieldByName;
import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;

@Slf4j
public class EvaluateOnRemainingFieldsStrategy implements EvaluationStrategy{

    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        String targetAggregationField = ClauseFieldComparatorConfig.getTargetField(clause.getClauseField().getLawFieldName());
        Class<?> lawFieldValueToken = clause.getToken();
        Class<?> clazz = getFloorClass(targetAggregationField);

        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                targetAggregationField, clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());

        if(!lawFieldValueToken.equals(clazz)) {
            throw new UnsupportedOperationException("non matching token types of target floor Field Types in aggregation");
        }

        List<Field> aggregatedFloorFields = survivingFloors.stream()
                .map(floorResults -> getFloorFieldByName(floorResults.getFloor(), targetAggregationField)).collect(Collectors.toList());

        T lawValue = (T) clause.getValue();
        T result = aggregate(aggregatedFloorFields);

        ComparableComparisonStrategy<T> strategy = new ComparableComparisonStrategy<>();
        return strategy.compare(result, lawValue, clause.getComparisonOperator());
    }
}
