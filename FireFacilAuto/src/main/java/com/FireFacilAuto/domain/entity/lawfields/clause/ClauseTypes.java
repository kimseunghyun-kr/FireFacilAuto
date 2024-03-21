package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorClauses;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum ClauseTypes {
    BuildingClauses(PossibleBuildingClauses.class),
    FloorClauses(PossibleFloorClauses.class);

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
