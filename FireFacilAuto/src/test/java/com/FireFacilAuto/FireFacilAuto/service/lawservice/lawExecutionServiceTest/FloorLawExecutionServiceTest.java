package com.FireFacilAuto.FireFacilAuto.service.lawservice.lawExecutionServiceTest;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorAttributes;
import com.FireFacilAuto.domain.entity.lawfields.ApplicationMethod;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawBuilder;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.floorLaws.FloorLawExecutionService;
import com.FireFacilAuto.service.lawService.floorLaws.FloorLawRepositoryService;
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingClassification;
import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingSpecification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorClassification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorSpecification;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
public class FloorLawExecutionServiceTest {
    @InjectMocks
    private final FloorLawExecutionService flawService;

    @MockBean
    private final FloorLawRepositoryService buildingLawRepositoryService;

    private static final List<FloorLawFields> testBuildingLawList = new LinkedList<>();

    private static final FloorLawBuilder floorLawBuilder = new FloorLawBuilder();

    @Autowired
    public FloorLawExecutionServiceTest(FloorLawExecutionService blawService, FloorLawExecutionService flawService, FloorLawRepositoryService buildingLawRepositoryService) {
        this.flawService = flawService;
        this.buildingLawRepositoryService = buildingLawRepositoryService;
    }

    @BeforeAll
    public static void FloorlawInitializr() {
        FloorLawFields tempSprinklerLaw = floorLawBuilder.setTargetlaw(1,7)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(2, -1)
                .buildThenReset();

        FloorLawFields sprinklerLaw2 = floorLawBuilder.setTargetlaw(1,7)
                .setApplicationMethod(ApplicationMethod.APPLICABLEONLY)
                .setTargetFloorPurpose(5, -1)
                .addFloorNo(7,ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .addFloorNo(8, ComparisonOperator.LESS_THAN_OR_EQUAL)
                .addFloorAreaThreshold(800.0 , ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        FloorLawFields alarmLaw1 = floorLawBuilder.setTargetlaw(2,3)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(2,-1)
                .addIsUnderground(true, ComparisonOperator.EQUAL)
                .addFloorNo(1, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .addFloorAreaThreshold(150.0, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        FloorLawFields alarmLaw2 = floorLawBuilder.setTargetlaw(2,3)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(3,-1)
                .addIsUnderground(true, ComparisonOperator.EQUAL)
                .addFloorNo(1, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .addFloorAreaSum(150.0, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();



        testBuildingLawList.add(tempSprinklerLaw);
        testBuildingLawList.add(sprinklerLaw2);
        testBuildingLawList.add(alarmLaw1);
        testBuildingLawList.add(alarmLaw2);

    }

    private List<FloorLawFields> mockCandidateLawFilter(Integer buildingClassification, Integer buildingSpecification) {
        return testBuildingLawList.stream().filter(flaw-> flaw.floorClassification.equals(-1) ||
                (flaw.floorClassification.equals(buildingClassification) && (flaw.floorSpecification.equals(-1) || flaw.floorSpecification.equals(buildingSpecification)))
        ).toList();
    }

    private List<FloorLawFields> getCandidateFloorLaws(List<FloorResults> floorResultsList, Set<Pair> floorResultStore) {
        List<FloorLawFields> candidateFloorLaws = new LinkedList<>();

        for (FloorResults floorResults : floorResultsList) {
            Floor floor = floorResults.getFloor();
            Pair p = new Pair(getFloorClassification(floor), getFloorSpecification(floor));
            if (floorResultStore.add(p)) {
                candidateFloorLaws.addAll(this.mockCandidateLawFilter(p.first(), p.second()));
            }
        }
        return candidateFloorLaws;
    }


    @Test
    public void testExecution() {
        // Given
        Building testBuilding1 = BuildingAttributes.builder()
                .buildingClassification(2)
                .buildingSpecification(-1)
                .buildingMaterial(1)
                .GFA(15000.0)
                .overgroundFloors(12)
                .undergroundFloors(3)
                .build().toBuilding();

        log.info("testBuilding1 : {}", testBuilding1);

        Floor testFloorB1 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorNo(1)
                .isUnderGround(true)
                .floorArea(500.0)
                .floorWindowAvailability(false)
                .build()

        List<Floor> testFloor = new LinkedList<>();
        testFloor.add(new Floor());
        testBuilding1.setCompositeFloorsList(testFloor);

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(testBuilding1);
        List<FloorResults> floorResultsList = resultSheet.getFloorResultsList();
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaws = getCandidateFloorLaws(floorResultsList, floorResultStore);

        // when
        flawService.floorLawExecute(testBuilding1, floorResultsList, candidateFloorLaws);

        //then
        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
//        assertThat(floorResultsList)
//                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
//                .containsOnly(true);

    }


}
