package com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.lawfields.LawEvaluator;

public class BuildingLawEvaluationStrategy implements LawEvaluator {
    private final Building building;

    public BuildingLawEvaluationStrategy(Building building) {
        this.building = building;
    }

    @Override
    public void evaluateLaw() {

    }
}
