package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building.TestBuildingObjectBuilder;
import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law.TestBuildingLawObjectBuilder;
import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;

import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.floorResultSheetBuilder;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class BuildingLawExecutionServiceTest {
    @InjectMocks
    private final BuildingLawExecutionService lawExecutionService;

    @MockBean
    private BuildingLawRepositoryService buildingLawRepositoryService;
    private Building testBuilding1;
    private BuildingLawFields Blaw1;
    private FloorLawFields Flaw1;

    private final TestBuildingObjectBuilder buildingGenerator = new TestBuildingObjectBuilder();
    private final TestBuildingLawObjectBuilder buildingLawObjectBuilder = new TestBuildingLawObjectBuilder();


    @Autowired
    public BuildingLawExecutionServiceTest(BuildingLawExecutionService lawExecutionService) {
        this.lawExecutionService = lawExecutionService;
    }

    @BeforeEach
    public void mockBuildingBuilder() {
        setTestBuildingtestcase1();


        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void manuallyMadeBuildingLawTest() {
        MockitoAnnotations.openMocks(this);

        // Given
        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(testBuilding1);
        List<FloorResults> floorResultsList = floorResultSheetBuilder(testBuilding1);
        List<BuildingLawFields> candidateBuildingLaw = new ArrayList<>(List.of(new BuildingLawFields[]{this.Blaw1}));

        // Mock the behavior of the lawService
        when(buildingLawRepositoryService.getLawsWithApplicablePurpose(testBuilding1)).thenReturn(candidateBuildingLaw);

        // Act
        lawExecutionService.buildingLawExecute(testBuilding1, floorResultsList);

        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(resultSheet.getFloorResultsList())
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
                .containsOnly(true);

    }


    @Test
    public void RandomGeneratedBuildingTest() {
        Building building = buildingGenerator.autoBuildingObjectBuilder();
        log.info("Building {}, ", building);
    }

    @Test
    public void RandomGeneratedLawTest() {
        List<BuildingLawFields> blf = buildingLawObjectBuilder.randomBuildingLawsBuilder(1);
        log.info("blf test {}", blf);
    }

    private void setTestBuildingtestcase1() {
        Address address = new Address();
        address.setDetailAdr("");
        address.setStreetAdr("서울 강남구 학동로11길 29");
        address.setZipCode("zipCode=06043");
        address.setSigunguCode("11680");
        address.setBcode("10800");
        address.setJi("0011");
        address.setBun("0024");

        testBuilding1 = BuildingAttributes.builder()
                .buildingMaterial(1)
                .buildingClassification(1)
                .buildingSpecification(1)
                .buildingHumanCapacity(100)
                .gfa(1000.0)
                .approvalDate(LocalDate.now())
                .undergroundFloors(1)
                .overgroundFloors(1)
                .build();


        testBuilding1.setJuso(address);



        Floor testBuilding1Composed1F = new Floor();
        testBuilding1Composed1F.setBuilding(testBuilding1);
        testBuilding1Composed1F.setFloorNo(1);
        testBuilding1Composed1F.setIsUnderGround(false);
        testBuilding1Composed1F.setFloorClassification(1);
        testBuilding1Composed1F.setFloorSpecification(1);
        testBuilding1Composed1F.setFloorArea(700.0);
        testBuilding1Composed1F.setFloorWindowAvailability(true);
        testBuilding1Composed1F.setFloorMaterial(1);

        Floor testBuilding1ComposedB1F = new Floor();
        testBuilding1ComposedB1F.setBuilding(testBuilding1);
        testBuilding1ComposedB1F.setFloorNo(1);
        testBuilding1ComposedB1F.setIsUnderGround(true);
        testBuilding1ComposedB1F.setFloorClassification(2);
        testBuilding1ComposedB1F.setFloorSpecification(2);
        testBuilding1ComposedB1F.setFloorArea(300.0);
        testBuilding1ComposedB1F.setFloorWindowAvailability(false);
        testBuilding1ComposedB1F.setFloorMaterial(1);

        testBuilding1.setCompositeFloorsList(List.of(new Floor[]{testBuilding1Composed1F, testBuilding1ComposedB1F}));

        this.Blaw1 = new BuildingLawFields();
        Blaw1.setBuildingClassification(-1);
        Blaw1.setBuildingSpecification(-1);
        Blaw1.setGFA(33.0);
        Blaw1.setMajorCategoryCode(1);
        Blaw1.setMinorCategoryCode(1);

        Conditions Blaw1Condition = new Conditions();
        Blaw1Condition.setBuildingLawFields(Blaw1);
        Blaw1Condition.setFieldName("GFA");
        Blaw1Condition.setOperator(ComparisonOperator.GREATER_THAN_OR_EQUAL);

        Blaw1.setConditionsList(List.of(new Conditions[]{Blaw1Condition}));

        this.Flaw1 = new FloorLawFields();
        Flaw1.setFloorClassification(-1);
        Flaw1.setFloorSpecification(-1);
        Flaw1.setMajorCategoryCode(2);
        Flaw1.setMinorCategoryCode(1);
        Flaw1.setFloorAreaThreshold(500.0);

        Conditions Flaw1Condition = new Conditions();
        Flaw1Condition.setFloorLawFields(Flaw1);
        Flaw1Condition.setFieldName("floorAreaThreshold");
        Flaw1Condition.setOperator(ComparisonOperator.GREATER_THAN_OR_EQUAL);

        Flaw1.setConditionsList(List.of(new Conditions[]{Flaw1Condition}));
    }



}
