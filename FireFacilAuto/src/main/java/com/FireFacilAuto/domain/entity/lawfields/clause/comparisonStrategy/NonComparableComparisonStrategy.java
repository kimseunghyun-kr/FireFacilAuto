package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

// ConcreteStrategy2 for non-Comparable types
public class NonComparableComparisonStrategy<T> implements ComparisonStrategy<T> {
    @Override
    public Boolean compare(T input, T value, ComparisonOperator comparisonOperator) {
        // Implement special handling for non-Comparable types (e.g., String)
        switch (comparisonOperator) {
            case EQUAL -> {
                return input.equals(value);
            }
            case NOT_EQUAL -> {
                return !input.equals(value);
                // Add more cases as needed
            }
            default -> throw new IllegalArgumentException("Unsupported operator: " + comparisonOperator);
        }
    }
}
