package com.FireFacilAuto.FireFacilAuto.service.lawservice.lawExecutionServiceTest;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.ApplicationMethod;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawBuilder;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static com.FireFacilAuto.domain.entity.building.BuildingUtils.*;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.floorResultSheetBuilder;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class BuildingLawExecutionServiceTest {

    @InjectMocks
    private final BuildingLawExecutionService blawService;

    @MockBean
    private final BuildingLawRepositoryService buildingLawRepositoryService;

    private static final List<BuildingLawFields> testBuildingLawList = new LinkedList<>();

    private static final BuildingLawBuilder buildingLawBuilder = new BuildingLawBuilder();

    @Autowired
    public BuildingLawExecutionServiceTest(BuildingLawExecutionService blawService, BuildingLawRepositoryService buildingLawRepositoryService) {
        this.blawService = blawService;
        this.buildingLawRepositoryService = buildingLawRepositoryService;
    }

    @BeforeAll
    public static void BuildingLawInitializr() {
        BuildingLawFields fireExtinguisherLaw = buildingLawBuilder.setTargetBuilding(-1,-1).setTargetlaw(1,1)
                .setApplicationMethod(ApplicationMethod.ALL)
                .addGFA(33, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        BuildingLawFields sprinklerLaw = buildingLawBuilder.setTargetBuilding(3,-1).setTargetlaw(2,1)
                .setApplicationMethod(ApplicationMethod.ALL)
                .addGFA(5000, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        BuildingLawFields sprinklerLaw2 = buildingLawBuilder.setTargetBuilding(4,-1).setTargetlaw(2,1)
                .setApplicationMethod(ApplicationMethod.ALL)
                .addGFA(5000, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .buildThenReset();

        testBuildingLawList.add(fireExtinguisherLaw);
        testBuildingLawList.add(sprinklerLaw);
        testBuildingLawList.add(sprinklerLaw2);
    }

    private List<BuildingLawFields> mockCandidateLawFilter(Integer buildingClassification, Integer buildingSpecification) {
        return testBuildingLawList.stream().filter(blaw-> blaw.buildingClassification.equals(-1) ||
                (blaw.buildingClassification.equals(buildingClassification) && (blaw.buildingSpecification.equals(-1) || blaw.buildingSpecification.equals(buildingSpecification)))
                ).toList();
    }

    @Test
    public void testExecution() {
        // Given
        Building testBuilding1 = BuildingAttributes.builder()
                .buildingClassification(1)
                .buildingSpecification(1)
                .buildingMaterial(1)
                .GFA(2000.0)
                .build().toBuilding();

        log.info("testBuilding1 : {}", testBuilding1);

        List<Floor> testFloor = new LinkedList<>();
        testFloor.add(new Floor());
        testBuilding1.setCompositeFloorsList(testFloor);

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(testBuilding1);
        List<FloorResults> floorResultsList = resultSheet.getFloorResultsList();
        List<BuildingLawFields> candidateBuildingLaw = mockCandidateLawFilter(getBuildingClassification(testBuilding1),
                getBuildingSpecification(testBuilding1));

        when(buildingLawRepositoryService.getLawsWithApplicablePurpose(testBuilding1)).thenReturn(candidateBuildingLaw);

        // when
        blawService.buildingLawExecute(testBuilding1, floorResultsList);

        //then
        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(floorResultsList)
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
                .containsOnly(true);

    }
}
