package com.FireFacilAuto.domain.lawExecution;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;

public class BuildingLaw extends Law{

    @Override
    public Boolean evaluateBuilding(Building building) {
        return null;
    }

    @Override
    public Boolean evaluateFloor(Floor floor) {
        return null;
    }
}
