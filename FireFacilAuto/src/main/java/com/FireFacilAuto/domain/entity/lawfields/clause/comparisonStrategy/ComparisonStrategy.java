package com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy;

import org.hibernate.query.sqm.ComparisonOperator;

public interface ComparisonStrategy<T>{
    Boolean compare(T input, T value, ComparisonOperator comparisonOperator);
}
