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
@DiscriminatorValue("Integer")
public class IntegerClauseValueWrapper extends ClauseValueWrapper{
    @Column(name="field_value")
    Integer value;
    public IntegerClauseValueWrapper(Integer value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }
}
