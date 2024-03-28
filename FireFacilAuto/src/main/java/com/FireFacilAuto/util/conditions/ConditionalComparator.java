package com.FireFacilAuto.util.conditions;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class ConditionalComparator {
    public static <T> boolean isActivated(T value) {
        // Customize this method based on your criteria for activated (non-default) values
        if(value != null) {
            log.info("defaultValue = {} , value = {}, match = {}", getDefaultValue(value.getClass()), value, value.equals(getDefaultValue(value.getClass())));
        }

        if(value instanceof Boolean) {
            return true;
        }
        return value != null && !value.equals(getDefaultValue(value.getClass()));
    }

    public static <T> T getDefaultValue(Class<T> clazz) {
        // Provide default values based on the type
        if (clazz.equals(LocalDate.class)) {
            return (T) LocalDate.MIN;
        } else if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(-1);
        } else if (Number.class.isAssignableFrom(clazz)) {
            return (T) Double.valueOf(-1); // Assuming -1 is the default value for Double
        } else if (clazz.equals(Boolean.class)) {
            return null;
        } else if (clazz.equals(String.class)) {
            return (T)"";
        } else {
            throw new IllegalArgumentException("Unsupported type: " + clazz);
        }
    }


}
