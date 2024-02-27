package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;

import java.util.List;

public class ResultSheetInitializingUtils {
    static List<FloorResults> floorResultSheetBuilder(Building building) {
        return building.getCompositeFloors().stream().map(FloorResults::floorFactory).toList();
    }

    static ResultSheet resultSheetInitializr(Building building) {
        ResultSheet resultSheet = new ResultSheet();
        resultSheet.setBuilding(building);
        return resultSheet;
    }


}
