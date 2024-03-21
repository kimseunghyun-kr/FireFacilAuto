package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorClauses;

import java.util.HashSet;

public class AggregationOperationConfigList {
    private static final HashSet<PossibleClauses> aggregationClauses = new HashSet<>();

    static {
        aggregationClauses.add(PossibleFloorClauses.FLOOR_AREA_SUM);
    }
    public static Boolean isClauseAnAggregate (PossibleClauses clause) {
        return aggregationClauses.contains(clause);
    }

}
