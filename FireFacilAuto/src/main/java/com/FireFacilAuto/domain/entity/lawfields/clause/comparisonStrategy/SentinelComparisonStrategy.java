package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

public class SentinelComparisonStrategy<T> implements ComparisonStrategy<T>{
    @Override
    public Boolean compare(Object input, Object value, ComparisonOperator comparisonOperator) {
        throw new UnsupportedOperationException("this is an unsupported resolution method, " +
                "this can potentially be due to a registered class in ComparisonStrategyConfig not" +
                "having an appropriate comparisonStrategy being assigned" +
                "or random bugs. but definitely related to the comparisonStrategies. good luck hunting");
    }
}
