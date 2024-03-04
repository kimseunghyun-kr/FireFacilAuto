package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.PossibleLawFieldRegistry;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClauseFactory {

    private final PossibleLawFieldRegistry registry;

    @Autowired
    public ClauseFactory(PossibleLawFieldRegistry registry) {
        this.registry = registry;
    }

    public <T,E extends Enum<E> & PossibleLawField> Clause<T> createClause(String field, Class<E> enumType, ComparisonOperator co, T input, int priority) {
        PossibleLawField lawField = registry.get(enumType, field);
        if (lawField == null) {
            throw new IllegalArgumentException("Invalid field name: " + field);
        }
        return new Clause<T>(lawField, co, input, priority, lawField.getFieldType().getSimpleName());
    }
}
