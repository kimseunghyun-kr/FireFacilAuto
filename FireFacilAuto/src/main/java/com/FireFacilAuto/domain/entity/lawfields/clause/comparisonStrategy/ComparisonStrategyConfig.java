package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ComparisonStrategyConfig {
    private static final ConcurrentHashMap<String, ComparisonStrategy<?>> fieldStrategies = new ConcurrentHashMap<>();

    public <T extends Comparable<T>,U> ComparisonStrategy<?> resolveStrategy (String clauseField, Class<?> clazz) {
        if(fieldStrategies.containsKey(clauseField)) {
            return fieldStrategies.getOrDefault(clauseField, new SentinelComparisonStrategy<T>());
        }
        if (Comparable.class.isAssignableFrom(clazz)) {
            return new ComparableComparisonStrategy<T>();
        } else {
            return new NonComparableComparisonStrategy<U>();
        }
    }

}

