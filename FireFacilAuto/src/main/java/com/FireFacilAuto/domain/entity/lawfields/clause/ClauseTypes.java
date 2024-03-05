package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import lombok.Getter;

@Getter
public enum ClauseTypes {
    PossibleBuildingClauses(PossibleBuildingClauses.class),
    PossibleFloorClauses(PossibleClauses.class);

    private final Class<? extends PossibleClauses> lawType;

    ClauseTypes(Class<? extends PossibleClauses> lawType) {
        this.lawType = lawType;
    }

    public PossibleClauses getClauseByName(String clauseName) {
        for (PossibleClauses possibleClause : lawType.getEnumConstants()) {
            if (possibleClause.getLawFieldName().equals(clauseName)) {
                return possibleClause;
            }
        }
        return null; // Clause not found
    }
}
