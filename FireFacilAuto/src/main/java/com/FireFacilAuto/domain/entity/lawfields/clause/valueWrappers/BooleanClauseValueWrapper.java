package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("Boolean")
public class BooleanClauseValueWrapper extends ClauseValueWrapper {
    Boolean value;
    public BooleanClauseValueWrapper(Boolean value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
