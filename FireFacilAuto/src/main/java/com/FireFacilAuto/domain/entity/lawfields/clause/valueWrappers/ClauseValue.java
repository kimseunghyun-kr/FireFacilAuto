package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;


import lombok.Getter;

import java.time.LocalDate;

@Getter
public enum ClauseValue {
    STRING(String.class),
    LOCAL_DATE(LocalDate.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    BOOLEAN(Boolean.class);

    private final Class<?> correspondingClass;

    ClauseValue(Class<?> correspondingClass) {
        this.correspondingClass = correspondingClass;
    }

}
