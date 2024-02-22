package com.FireFacilAuto.util.conditions;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
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
        return evaluate(appliedValue, operator.sqlText(), fieldValue) && isActivated(fieldValue);
    }

    public static <T> boolean isActivated(T value) {
        // Customize this method based on your criteria for activated (non-default) values
        if(value != null) {
            log.info("defaultValue = {} , value = {}, match = {}", getDefaultValue(value.getClass()), value, value.equals(getDefaultValue(value.getClass())));
        }
        return value != null && !value.equals(getDefaultValue(value.getClass()));
    }

    public static <T> T getDefaultValue(Class<T> clazz) {
        // Provide default values based on the type
        if (clazz.equals(LocalDate.class)) {
            return (T) LocalDate.MIN;
        } else if (Number.class.isAssignableFrom(clazz)) {
            return (T) Double.valueOf(-1); // Assuming -1 is the default value for Double
        } else {
            throw new IllegalArgumentException("Unsupported type: " + clazz);
        }
    }


}
