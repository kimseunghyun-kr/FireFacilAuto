package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

public class StringComparisonStrategy<Q> implements ComparisonStrategy<Q> {
    @Override
    public Boolean compare(Q input, Q value, ComparisonOperator comparisonOperator) {
        String fieldInput = (String) input;
        String valueToCompare = (String)value;

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
