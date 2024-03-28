package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

public class StringComparisonStrategy implements ComparisonStrategy<String> {
    @Override
    public Boolean compare(String input, String value, ComparisonOperator comparisonOperator) {

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
