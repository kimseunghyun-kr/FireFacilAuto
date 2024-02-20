package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.service.lawService.BuildingLawExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildingLawExecutionServiceTest {
    private final BuildingLawExecutionService buildingLawExecutionService;

    @Autowired
    public BuildingLawExecutionServiceTest(BuildingLawExecutionService buildingLawExecutionService) {
        this.buildingLawExecutionService = buildingLawExecutionService;
    }

//    @BeforeEach
//    public Building MockBuildingCreator() {
//
//    }
//
//    @Test



}
