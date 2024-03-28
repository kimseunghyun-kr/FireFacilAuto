package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorUtils;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.*;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.*;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


@Slf4j
public class ClauseEvaluator {
    private static final List<FloorResults> EMPTYSENTINELLIST = new LinkedList<>();


//    <T extends Number & Comparable<T>, U extends Comparable<U>,V>
    @Deprecated
    public static Boolean evaluateSingleBuilding(Clause clause, Building building) {
        String targetField = clause.clauseField.getTargetFieldName();
        EvaluationType evaluationType = clause.getEvaluationType();
        EvaluationStrategy strategyToUse = new SentinelEvaluationStrategy();
        Field field = BuildingUtils.getBuildingFieldByName(building, targetField);

        if (Objects.requireNonNull(evaluationType) == EvaluationType.SINGLE) {
            strategyToUse = new EvalutateOnSingleFieldStrategy();
        }

        // left this way as in the future Building may have aggregate? fields or other methods of evaluation. as of current,
        // only valid way is singular

        return strategyToUse.evaluate(clause, field);
    }

    @Deprecated
    public static <T extends Number & Comparable<T>> List<FloorResults> evaluateFloor(Clause clause, Building building, List<FloorResults> survivingFloors) {
        EvaluationType evaluationType = clause.getEvaluationType();
        String targetField = clause.clauseField.getTargetFieldName();
        if(evaluationType.equals(EvaluationType.SINGLE)) {
            List<FloorResults> nextEpochSurvivor = new LinkedList<>(survivingFloors.stream()
                    .filter(survivingFloor -> {
                        Field field = FloorUtils.getFloorFieldByName(survivingFloor.getFloor(), targetField);
                        return new EvalutateOnSingleFieldStrategy().evaluate(clause, field);
                    })
                    .toList());
            return nextEpochSurvivor;
        }

        if(evaluationType.equals(EvaluationType.AGGREGATE_ALL)) {
            Field[] field = building.getCompositeFloorsList().stream()
                    .map(buildingFloor -> FloorUtils.getFloorFieldByName(buildingFloor, targetField))
                    .toArray(Field[]::new);
            if(new EvaluateByAggregateFieldStrategy<T>().evaluate(clause,field)) {
                return survivingFloors;
            } else {
                return EMPTYSENTINELLIST;
            }
        }

        if(evaluationType.equals(EvaluationType.AGGREGATE_REMAINING)) {
            Field[] field = survivingFloors.stream()
                    .map(survivingFloor -> FloorUtils.getFloorFieldByName(survivingFloor.getFloor(), targetField))
                    .toArray(Field[]::new);
            if(new EvaluateByAggregateFieldStrategy<T>().evaluate(clause,field)) {
                return survivingFloors;
            } else {
                return EMPTYSENTINELLIST;
            }
        }

        throw new UnsupportedOperationException("this is an unsupported evaluation option for Floors. please re-evaluate what is going on");
    }

    @Deprecated
    public static Boolean evaluateSingleFloor(Clause clause, Floor floor) {
        EvaluationType evaluationType = clause.getEvaluationType();
        String targetField = clause.clauseField.getTargetFieldName();
        if(!evaluationType.equals(EvaluationType.SINGLE)) {
            throw new UnsupportedOperationException("calling a evaluateSingleFloor on a non singular evaluation denoted clause");
        }
        Field field = FloorUtils.getFloorFieldByName(floor, targetField);
        return new EvalutateOnSingleFieldStrategy().evaluate(clause,field);
    }

    @Deprecated
    public static Boolean evaluateAggregateAll(Clause clause, Building building) {
        EvaluationType evaluationType = clause.getEvaluationType();
        String targetField = clause.clauseField.getTargetFieldName();
        if(!evaluationType.equals(EvaluationType.AGGREGATE_ALL)) {
            throw new UnsupportedOperationException("calling a evaluateAggregateRemaining on a non aggregateAll evaluation denoted clause");
        }
        Field[] field = building.getCompositeFloorsList().stream()
                .map(floor -> FloorUtils.getFloorFieldByName(floor, targetField))
                .toArray(Field[]::new);
        return new EvalutateOnSingleFieldStrategy().evaluate(clause,field);
    }

    @Deprecated
    public static Boolean evaluateAggregateRemaining(Clause clause, List<FloorResults> survivingFloors) {
        EvaluationType evaluationType = clause.getEvaluationType();
        String targetField = clause.clauseField.getTargetFieldName();
        if(!evaluationType.equals(EvaluationType.AGGREGATE_REMAINING)) {
            throw new UnsupportedOperationException("calling a evaluateAggregateRemaining on a non aggregateRemaining evaluation denoted clause");
        }
        Field[] field = survivingFloors.stream()
                .map(floorResults -> FloorUtils.getFloorFieldByName(floorResults.getFloor(), targetField))
                .toArray(Field[]::new);
        return new EvalutateOnSingleFieldStrategy().evaluate(clause,field);
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
