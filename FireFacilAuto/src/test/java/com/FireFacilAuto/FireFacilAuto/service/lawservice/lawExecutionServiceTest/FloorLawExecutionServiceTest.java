package com.FireFacilAuto.FireFacilAuto.service.lawservice.lawExecutionServiceTest;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawBuilder;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawBuilder;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawRepositoryService;
import com.FireFacilAuto.service.lawService.floorLaws.FloorLawExecutionService;
import com.FireFacilAuto.service.lawService.floorLaws.FloorLawRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@SpringBootTest
public class FloorLawExecutionServiceTest {
    @InjectMocks
    private final FloorLawExecutionService blawService;

    @MockBean
    private final FloorLawRepositoryService buildingLawRepositoryService;

    private static final List<FloorLawFields> testBuildingLawList = new LinkedList<>();

    private static final FloorLawBuilder floorLawBuilder = new FloorLawBuilder();

    @Autowired
    public FloorLawExecutionServiceTest(FloorLawExecutionService blawService, FloorLawRepositoryService buildingLawRepositoryService) {
        this.blawService = blawService;
        this.buildingLawRepositoryService = buildingLawRepositoryService;
    }

    @BeforeAll
    public static void BuildingLawInitializr() {
        FloorLawFields tempSprinklerLaw = floorLawBuilder.setTargetlaw(1,7).setTargetFloor(2, -1)

        testBuildingLawList.add(tempSprinklerLaw);
    }
}
