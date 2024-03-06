package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LocalDateClauseValueWrapper extends ClauseValueWrapper {
    LocalDate value;
    public LocalDateClauseValueWrapper(LocalDate value, ClauseValue typeToken) {
        super(typeToken);
        this.value = value;
    }

}
