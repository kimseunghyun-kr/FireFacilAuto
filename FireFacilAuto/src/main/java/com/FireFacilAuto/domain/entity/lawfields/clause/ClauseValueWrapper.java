package com.FireFacilAuto.domain.entity.lawfields.clause;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ClauseValueWrapper<T> {
    T value;
    String valueType;

    public ClauseValueWrapper(T value, String valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public ClauseValueWrapper() {

    }
}
