package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("Double")
public class DoubleClauseValueWrapper extends ClauseValueWrapper{
    @Column(name="field_value")
    Double value;
    public DoubleClauseValueWrapper(Double value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
