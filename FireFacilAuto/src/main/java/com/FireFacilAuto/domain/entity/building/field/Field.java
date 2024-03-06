package com.FireFacilAuto.domain.entity.building.field;

import jakarta.persistence.*;

import lombok.extern.slf4j.Slf4j;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "field_type")
@Entity
@Slf4j
public abstract class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract Object getValue();
    public abstract String getFieldName();
    public abstract Class<?> getValueType();


}
