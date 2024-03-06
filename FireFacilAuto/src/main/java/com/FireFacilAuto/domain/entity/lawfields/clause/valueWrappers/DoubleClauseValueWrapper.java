package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("Double")
public class DoubleClauseValueWrapper extends ClauseValueWrapper{
    Double value;
    public DoubleClauseValueWrapper(Double value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
