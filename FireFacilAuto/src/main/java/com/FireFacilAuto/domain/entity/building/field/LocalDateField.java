package com.FireFacilAuto.domain.entity.building.field;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "Local_Date")
public class LocalDateField extends Field{

    public String fieldName;
    @Column(name = "field_value")
    public LocalDate value;
    public Class<LocalDate> valueType;

    public LocalDateField(String fieldName, LocalDate value, Class<LocalDate> valueType) {
        this.fieldName = fieldName;
        this.value = value;
        this.valueType = valueType;
    }

}
