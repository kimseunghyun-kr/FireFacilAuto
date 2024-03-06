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
@DiscriminatorValue(value = "Double")
public class DoubleField extends Field{

    public String fieldName;
    @Column(name = "field_value")
    public Double value;
    public Class<Double> valueType;
    @Id
    private Long id;

    public DoubleField(String fieldName, Double value, Class<Double> valueType) {
        this.fieldName = fieldName;
        this.value = value;
        this.valueType = valueType;
    }


    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public Class<?> getValueType() {
        return this.valueType;
    }

}
