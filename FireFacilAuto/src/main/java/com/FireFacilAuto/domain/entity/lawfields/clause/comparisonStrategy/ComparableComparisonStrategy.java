package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

// ConcreteStrategy1 for Comparable types
public class ComparableComparisonStrategy<T extends Comparable<T>> implements ComparisonStrategy<T> {
    @Override
    public Boolean compare(T input, T value, ComparisonOperator comparisonOperator) {
        return switch (comparisonOperator) {
            case EQUAL -> input.equals(value);
            case NOT_EQUAL -> !input.equals(value);
            case GREATER_THAN -> input.compareTo(value) > 0;
            case LESS_THAN -> input.compareTo(value) < 0;
            case GREATER_THAN_OR_EQUAL -> input.compareTo(value) >= 0;
            case LESS_THAN_OR_EQUAL -> input.compareTo(value) <= 0;
            default -> throw new IllegalArgumentException("Unsupported operator: " + comparisonOperator);
        };
    }
}

