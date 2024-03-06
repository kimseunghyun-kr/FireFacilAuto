package com.FireFacilAuto.domain.entity.building.field;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "Integer")
public class IntegerField extends Field{

    public String fieldName;
    @Column(name = "field_value")
    public Integer value;
    public Class<Integer> valueType;

    public IntegerField(String fieldName, Integer value, Class<Integer> valueType) {
        this.fieldName = fieldName;
        this.value = value;
        this.valueType = valueType;
    }

}
