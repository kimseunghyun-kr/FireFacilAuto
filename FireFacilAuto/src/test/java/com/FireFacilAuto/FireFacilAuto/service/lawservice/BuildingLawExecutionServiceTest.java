package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.installation.*;
import com.FireFacilAuto.service.lawService.BuildingLawExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class BuildingLawExecutionServiceTest {
    private final BuildingLawExecutionService buildingLawExecutionService;
    private Building building;

    @Autowired
    public BuildingLawExecutionServiceTest(BuildingLawExecutionService buildingLawExecutionService) {
        this.buildingLawExecutionService = buildingLawExecutionService;
    }

    @BeforeEach
    public void mockBuildingBuilder() {
        Address address = new Address();
        address.setDetailAdr("");
        address.setStreetAdr("서울 강남구 학동로11길 29");
        address.setZipCode("zipCode=06043");
        address.setSigunguCode("11680");
        address.setBcode("10800");
        address.setJi("0011");
        address.setBun("0024");
    }

//    @Test



}
