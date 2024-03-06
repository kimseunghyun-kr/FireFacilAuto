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
@DiscriminatorValue(value = "String")
public class StringField extends Field{

    public String fieldName;
    @Column(name = "field_value")
    public String value;
    public Class<String> valueType;

    public StringField(String fieldName, String value, Class<String> valueType) {
        this.fieldName = fieldName;
        this.value = value;
        this.valueType = valueType;
    }


}
