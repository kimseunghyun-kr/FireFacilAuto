package com.FireFacilAuto.domain.entity.lawfields.clause;

public interface PossibleClauses {
    String getLawFieldName();
    String getTargetFieldName();
    Class<?> getFieldType();
    ClauseTypes LawType();

}
