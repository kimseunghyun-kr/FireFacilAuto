package com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ClauseValueWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClauseValue valueType;

    public ClauseValueWrapper(ClauseValue valueType) {
        this.valueType = valueType;
    }
    public ClauseValueWrapper() {

    }

    public abstract Object getValue();

    public static <T> ClauseValueWrapper clauseValueWrapperfactory(ClauseValue cv, T value) {

        if(!value.getClass().equals(cv.getCorrespondingClass())) {
            throw new UnsupportedOperationException("the value provided does not match with the valuetype input provided, where valuetype :" + cv + "value :" + value);
        }
        if(cv.equals(ClauseValue.STRING)) {
            return new StringClauseValueWrapper((String) value, cv);
        }
        if(cv.equals(ClauseValue.INTEGER)) {
            return new IntegerClauseValueWrapper((Integer) value, cv);
        }
        if(cv.equals(ClauseValue.DOUBLE)) {
            return new DoubleClauseValueWrapper((Double) value,cv);
        }
        if(cv.equals(ClauseValue.LOCAL_DATE)) {
            return new LocalDateClauseValueWrapper((LocalDate) value, cv);
        }
        if(cv.equals(ClauseValue.BOOLEAN)) {
            return new BooleanClauseValueWrapper((Boolean) value, cv);
        }

        throw new UnsupportedOperationException("no corresponding matching types were found, where valuetype :" + cv + "value :" + value);

    }



}
