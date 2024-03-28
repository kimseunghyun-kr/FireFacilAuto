package com.FireFacilAuto.FireFacilAuto.service.lawservice.lawExecutionServiceTest;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorAttributes;
import com.FireFacilAuto.domain.entity.lawfields.ApplicationMethod;
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

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorClassification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorSpecification;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;
import static org.assertj.core.api.Assertions.assertThat;

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
        FloorLawFields tempSprinklerLaw = floorLawBuilder.setTargetlaw(1,6)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(2, -1)
                .buildThenReset();

        FloorLawFields sprinklerLaw2 = floorLawBuilder.setTargetlaw(1,7)
                .setApplicationMethod(ApplicationMethod.APPLICABLEONLY)
                .setTargetFloorPurpose(5, -1)
                .next()
                .addFloorNo(7,ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .next()
                .addFloorNo(8, ComparisonOperator.LESS_THAN_OR_EQUAL)
                .next()
                .addFloorAreaThreshold(800.0 , ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        FloorLawFields alarmLaw1 = floorLawBuilder.setTargetlaw(2,3)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(2,-1)
                .next()
                .addIsUnderground(true, ComparisonOperator.EQUAL)
                .next()
                .addFloorNo(1, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .next()
                .addFloorAreaThreshold(150.0, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        FloorLawFields alarmLaw2 = floorLawBuilder.setTargetlaw(2,3)
                .setApplicationMethod(ApplicationMethod.ALL)
                .setTargetFloorPurpose(3,-1)
                .next()
                .addIsUnderground(true, ComparisonOperator.EQUAL)
                .next()
                .addFloorNo(1, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .next()
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
        Building testBuilding1 = testBuildingFloorConfig1Initializr();

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(testBuilding1);
        List<FloorResults> floorResultsList = resultSheet.getFloorResultsList();

        log.info("resolving law candidacy");
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaws = getCandidateFloorLaws(floorResultsList, floorResultStore);

        // when
        flawService.floorLawExecute(testBuilding1, floorResultsList, candidateFloorLaws);

        //then
        logFloorResultsList(resultSheet);
//        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(floorResultsList)
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getSprinklerApparatus())
                .containsOnly(true);

    }

    @Test
    public void testSprinklerLaw2_1() {
        // Given
        Building testBuilding2 = testBuildingFloorConfig2Initializr();

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(testBuilding2);
        List<FloorResults> floorResultsList = resultSheet.getFloorResultsList();

        log.info("resolving law candidacy");
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaws = getCandidateFloorLaws(floorResultsList, floorResultStore);

        // when
        flawService.floorLawExecute(testBuilding2, floorResultsList, candidateFloorLaws);

        //then
        logFloorResultsList(resultSheet);
//        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(floorResultsList)
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getSprinklerApparatus())
                .containsOnly(true);

    }

    private static void logFloorResultsList(ResultSheet resultSheet) {
        for (FloorResults floorResults : resultSheet.getFloorResultsList()) {
            log.info("--------------------------------");
            log.info("floor result at {}", floorResults);
            log.info("--------------------------------");
        }
    }

    private static Building testBuildingFloorConfig1Initializr() {

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
                .floorClassification(2)
                .floorSpecification(-1)
                .floorNo(1)
                .isUnderGround(true)
                .floorArea(500.0)
                .floorWindowAvailability(false)
                .build();
        Floor testFloorB2 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(18)
                .floorSpecification(-1)
                .floorNo(2)
                .isUnderGround(true)
                .floorArea(600.0)
                .floorWindowAvailability(false)
                .build();
        Floor testFloorB3 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(18)
                .floorSpecification(-1)
                .floorNo(3)
                .isUnderGround(true)
                .floorArea(700.0)
                .floorWindowAvailability(false)
                .build();


        List<Floor> testFloor = new LinkedList<>(List.of(testFloorB1, testFloorB2, testFloorB3));
        testBuilding1.setCompositeFloorsList(testFloor);

        log.info("testBuilding1 : {}", testBuilding1);
        return testBuilding1;
    }


    private static Building testBuildingFloorConfig2Initializr() {

        Building testBuilding1 = BuildingAttributes.builder()
                .buildingClassification(2)
                .buildingSpecification(-1)
                .buildingMaterial(1)
                .GFA(15000.0)
                .overgroundFloors(12)
                .undergroundFloors(3)
                .build().toBuilding();

        log.info("testBuilding1 : {}", testBuilding1);

        Floor testFloor6 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(2)
                .floorSpecification(-1)
                .floorNo(6)
                .isUnderGround(false)
                .floorArea(500.0)
                .floorWindowAvailability(false)
                .build();
        Floor testFloor7 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(5)
                .floorSpecification(-1)
                .floorNo(7)
                .isUnderGround(false)
                .floorArea(800.0)
                .floorWindowAvailability(false)
                .build();
        Floor testFloor8 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(5)
                .floorSpecification(-1)
                .floorNo(8)
                .isUnderGround(false)
                .floorArea(700.0)
                .floorWindowAvailability(false)
                .build();
        Floor testFloor9 = new FloorAttributes.FloorBuilder()
                .building(testBuilding1)
                .floorClassification(18)
                .floorSpecification(-1)
                .floorNo(9)
                .isUnderGround(false)
                .floorArea(700.0)
                .floorWindowAvailability(false)
                .build();


        List<Floor> testFloor = new LinkedList<>(List.of(testFloor6, testFloor7, testFloor8, testFloor9));
        testBuilding1.setCompositeFloorsList(testFloor);

        log.info("testBuilding1 : {}", testBuilding1);
        return testBuilding1;
    }


}
