package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StringClauseValueWrapper extends ClauseValueWrapper {
    String value;
    public StringClauseValueWrapper(String value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
