package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorUtils;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.*;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorFieldByName;
import static com.FireFacilAuto.domain.entity.floors.PossibleFloorFields.getFloorClass;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Slf4j
public class ClauseEvaluator {


//    <T extends Number & Comparable<T>, U extends Comparable<U>,V>
    public static Boolean evaluateSingleBuilding(Clause<?> clause, Building building) {
        String targetField = clause.clauseField.getTargetFieldName();
        Field<?> field = BuildingUtils.getBuildingFieldByName(building, targetField);
        return evaluateSingleFieldWithClause(field, clause);
    }

    public static Boolean evaluateSingleFloor(Clause<?> clause, Floor floor) {
        String targetField = clause.clauseField.getTargetFieldName();
        Field<?> field = FloorUtils.getFloorFieldByName(floor, targetField);
        return evaluateSingleFieldWithClause(field, clause);
    }

    private static Boolean evaluateSingleFieldWithClause(Field<?> field, Clause<?> clause) {
        if(isActivated(field.value())){
            log.warn("null value detected when not supposed to. check if environment is test or not." +
                    "if this is in production setting then something critical went wrong" +
                    "error at field : {}, clause {}, fields are intended to discard if null value present",field , clause);
            return true;
        }
        String lawField = clause.clauseField.getLawFieldName();
        Object lawValue = clause.getValue();
        Class<?> clazz = field.valueType();
        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                field.fieldName(), clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());
        return defaultComparisonStrategyApply(clause, lawValue, clazz, field);
    }

    public static <T extends Number & Comparable<T>> Boolean evaluateAggregateFieldWithClause(Clause<?> clause, List<FloorResults> survivingFloors, String lawField) {
        String targetAggregationField = ClauseFieldComparatorConfig.getTargetField(lawField);
        Class<?> lawFieldValueToken = clause.getToken();
        Class<?> clazz = getFloorClass(targetAggregationField);

        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                targetAggregationField, clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());

        if(!lawFieldValueToken.equals(clazz)) {
            throw new UnsupportedOperationException("non matching token types of target floor Field Types in aggregation");
        }

        List<Field<?>> aggregatedFloorFields = survivingFloors.stream()
                .map(floorResults -> getFloorFieldByName(floorResults.getFloor(), targetAggregationField)).collect(Collectors.toList());

        T lawValue = (T) clause.getValue();
        T result = aggregate(aggregatedFloorFields);

        ComparableComparisonStrategy<T> strategy = new ComparableComparisonStrategy<>();
        return strategy.compare(result, lawValue, clause.getComparisonOperator());
    }

//    <T extends Number & Comparable<T>, U extends Comparable<U>,V>




    private static <U extends Comparable<U>, V> Boolean defaultComparisonStrategyApply(Clause<?> clause, Object lawValue, Class<?> clazz, Field<?> field) {
        if (lawValue instanceof String && clazz.equals(String.class)) {
            return lawValue.equals(field.value());
        }
        if (Comparable.class.isAssignableFrom(clazz) && lawValue.getClass().equals(clazz)) {
            ComparableComparisonStrategy<U> strategy = new ComparableComparisonStrategy<>();
            return strategy.compare((U) field.value(), (U) lawValue, clause.getComparisonOperator());
            // Compare using the strategy
        }
        else {
            NonComparableComparisonStrategy<V> strategy = new NonComparableComparisonStrategy<>();
            return strategy.compare((V) field.value(), (V) lawValue, clause.getComparisonOperator());
        }
    }


    private static <U extends Number> U aggregate(List<Field<?>> aggregatedFields) {
        if (aggregatedFields.isEmpty()) {
            return null;
        }

        Number result = (Number) aggregatedFields.getFirst().value();
        for (int i = 1; i < aggregatedFields.size(); i++) {
            result = add((U) result, (U) aggregatedFields.get(i).value());
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
