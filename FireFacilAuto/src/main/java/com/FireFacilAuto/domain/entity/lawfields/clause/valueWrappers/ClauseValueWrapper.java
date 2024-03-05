package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@MappedSuperclass
public abstract class ClauseValueWrapper<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    T value;
    String valueType;


    public ClauseValueWrapper(T value, String valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public ClauseValueWrapper() {

    }


    public static <T> ClauseValueWrapper<T> createValueWrapper(T input, Class<?> inputTypeToken, Class<? extends ClauseValueWrapper<?>> wrapperClass) {
        String typeTokenString = inputTypeToken.getSimpleName();
        return switch (typeTokenString) {
            case "Integer" -> (ClauseValueWrapper<T>) new IntegerClauseValueWrapper((Integer) input, typeTokenString);
            case "Double" -> (ClauseValueWrapper<T>) new DoubleClauseValueWrapper((Double) input, typeTokenString);
            case "LocalDate" -> (ClauseValueWrapper<T>) new LocalDateClauseValueWrapper((LocalDate) input, typeTokenString);
            case "Boolean" -> (ClauseValueWrapper<T>) new BooleanClauseValueWrapper((Boolean) input, typeTokenString);
            case "String" -> (ClauseValueWrapper<T>) new StringClauseValueWrapper((String) input, typeTokenString);
            default ->
                    throw new IllegalArgumentException("Unsupported inputTypeToken: " + inputTypeToken.getSimpleName());
        };
    }


}
