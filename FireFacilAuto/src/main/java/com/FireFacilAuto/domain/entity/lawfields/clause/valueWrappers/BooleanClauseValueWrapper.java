package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Column;
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
    @Column(name="field_value")
    Boolean value;
    public BooleanClauseValueWrapper(Boolean value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
