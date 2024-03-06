package com.FireFacilAuto.domain.entity.building.field;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "Boolean")
public class BooleanField extends Field{

    public String fieldName;
    @Column(name = "field_value")
    public Boolean value;
    public Class<Boolean> valueType;
    @Id
    private Long id;

    public BooleanField(String fieldName, Boolean value, Class<Boolean> valueType) {
        this.fieldName = fieldName;
        this.value = value;
        this.valueType = valueType;
    }

}
