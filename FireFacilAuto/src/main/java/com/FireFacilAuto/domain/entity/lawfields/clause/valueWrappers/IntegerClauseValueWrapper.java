package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class IntegerClauseValueWrapper extends ClauseValueWrapper{
    Integer value;
    public IntegerClauseValueWrapper(Integer value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
