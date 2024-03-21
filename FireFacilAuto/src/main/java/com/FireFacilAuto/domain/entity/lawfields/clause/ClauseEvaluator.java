package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorUtils;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.*;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorFieldByName;
import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Slf4j
@Deprecated
public class ClauseEvaluator {


//    <T extends Number & Comparable<T>, U extends Comparable<U>,V>
    public static Boolean evaluateSingleBuilding(Clause clause, Building building) {
        String targetField = clause.clauseField.getTargetFieldName();
        EvaluationType evaluationType = clause.getEvaluationType();
        Field field = BuildingUtils.getBuildingFieldByName(building, targetField);
        return evaluateSingleFieldWithClause(field, clause);
    }

    public static Boolean evaluateSingleFloor(Clause clause, Floor floor) {
        String targetField = clause.clauseField.getTargetFieldName();
        Field field = FloorUtils.getFloorFieldByName(floor, targetField);
        return evaluateSingleFieldWithClause(field, clause);
    }
    public static Boolean evaluate(Clause clause, Building building) {
        String targetField = clause.clauseField.getTargetFieldName();
        Field field = FloorUtils.getFloorFieldByName(floor, targetField);
    }




    public static <U extends Comparable<U>, V> Boolean defaultComparisonStrategyApply(Clause clause, Object lawValue, Class<?> clazz, Field field) {
        if (lawValue instanceof String && clazz.equals(String.class)) {
            return lawValue.equals(field.getValue());
        }
        if (Comparable.class.isAssignableFrom(clazz) && lawValue.getClass().equals(clazz)) {
            ComparableComparisonStrategy<U> strategy = new ComparableComparisonStrategy<>();
            return strategy.compare((U) field.getValue(), (U) lawValue, clause.getComparisonOperator());
            // Compare using the strategy
        }
        else {
            NonComparableComparisonStrategy<V> strategy = new NonComparableComparisonStrategy<>();
            return strategy.compare((V) field.getValue(), (V) lawValue, clause.getComparisonOperator());
        }
    }


    public static <U extends Number> U aggregate(List<Field> aggregatedFields) {
        if (aggregatedFields.isEmpty()) {
            return null;
        }

        Number result = (Number) aggregatedFields.getFirst().getValue();
        for (int i = 1; i < aggregatedFields.size(); i++) {
            result = add((U) result, (U) aggregatedFields.get(i).getValue());
        }

        return (U) result;
    }

    private static <U extends Number> U add(U operand1, U operand2) {
        if (operand1 instanceof Integer) {
            return (U) Integer.valueOf(operand1.intValue() + operand2.intValue());
        } else if (operand1 instanceof Double) {
            return (U) Double.valueOf(operand1.doubleValue() + operand2.doubleValue());
        } else {
            // Handle other cases or throw an exception if not supported
            throw new UnsupportedOperationException("Unsupported number type");
        }
    }


}
