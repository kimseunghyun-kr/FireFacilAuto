package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import lombok.Getter;

@Getter
public class DoubleClauseValueWrapper extends ClauseValueWrapper{
    Double value;
    public DoubleClauseValueWrapper(Double value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
