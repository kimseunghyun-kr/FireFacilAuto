package com.FireFacilAuto.util.conditions;


import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDateTime;

public class ConditionalComparator {

    public static <T extends Comparable<T>> boolean evaluate(T operand1, ComparisonOperator operator, T operand2) {
        // Implementation of the evaluation logic
        // You may replace this with your actual comparison logic
        return switch (operator) {
            case EQUAL -> operand1.equals(operand2);
            case NOT_EQUAL -> !operand1.equals(operand2);
            case GREATER_THAN -> operand1.compareTo(operand2) > 0;
            case LESS_THAN -> operand1.compareTo(operand2) < 0;
            case GREATER_THAN_OR_EQUAL -> operand1.compareTo(operand2) >= 0;
            case LESS_THAN_OR_EQUAL -> operand1.compareTo(operand2) <= 0;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    public static <T extends Comparable<T>> boolean evaluate(T operand1, String operator, T operand2) {
        return switch (operator) {
            case "=" -> operand1.equals(operand2);
            case "<>" -> !operand1.equals(operand2);
            case ">" -> operand1.compareTo(operand2) > 0;
            case "<" -> operand1.compareTo(operand2) < 0;
            case ">=" -> operand1.compareTo(operand2) >= 0;
            case "<=" -> operand1.compareTo(operand2) <= 0;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    public static <T extends Comparable<T>> boolean conditionParser(ComparisonOperator operator, T fieldValue, T appliedValue) {
        return evaluate(fieldValue, operator.sqlText(), appliedValue) && isActivated(fieldValue);
    }

    public static <T> boolean isActivated(T value) {
        // Customize this method based on your criteria for activated (non-default) values
        return value != null && !value.equals(getDefaultValue(value.getClass()));
    }

    public static <T> T getDefaultValue(Class<T> clazz) {
        // Provide default values based on the type
        if (clazz.equals(LocalDateTime.class)) {
            return (T) LocalDateTime.MIN;
        } else if (Number.class.isAssignableFrom(clazz)) {
            return (T) Long.valueOf(-1); // Assuming -1 is the default value for Long
        } else {
            throw new IllegalArgumentException("Unsupported type: " + clazz);
        }
    }


}
