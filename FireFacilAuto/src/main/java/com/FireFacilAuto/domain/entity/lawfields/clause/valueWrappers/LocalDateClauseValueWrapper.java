package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("LocalDate")
public class LocalDateClauseValueWrapper extends ClauseValueWrapper {
    @Column(name="field_value")
    LocalDate value;
    public LocalDateClauseValueWrapper(LocalDate value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }

}
