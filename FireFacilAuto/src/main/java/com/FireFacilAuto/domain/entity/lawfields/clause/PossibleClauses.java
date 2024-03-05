package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;

public interface PossibleClauses {
    String getLawFieldName();
    String getTargetFieldName();
    Class<?> getFieldType();
    ClauseTypes LawType();
    Class<? extends ClauseValueWrapper<?>> getWrapper();

}
