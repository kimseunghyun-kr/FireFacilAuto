package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.*;
import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.BuildingAndFloorLawExecutionFacadeService;

import com.FireFacilAuto.service.lawService.LawService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
public class BuildingLawExecutionServiceTest {
    @InjectMocks
    private final BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService;

    @MockBean
    private LawService lawService;
    private Building testBuilding1;
    private BuildingLawFields Blaw1;
    private FloorLawFields Flaw1;

    private final TestBuildingObjectBuilder buildingGenerator = new TestBuildingObjectBuilder();
    private final TestFloorObjectBuilder floorObjectBuilder = new TestFloorObjectBuilder();
    private final TestBuildingLawObjectBuilder buildingLawObjectBuilder = new TestBuildingLawObjectBuilder();
    private final TestFloorLawObjectBuilder floorLawObjectBuilder = new TestFloorLawObjectBuilder();

    @Autowired
    public BuildingLawExecutionServiceTest(BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService) {
        this.lawExecutionFacadeService = lawExecutionFacadeService;
    }

    @BeforeEach
    public void mockBuildingBuilder() {
        setTestBuildingtestcase1();


        MockitoAnnotations.openMocks(this);
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

        this.testBuilding1 = new Building();
        testBuilding1.setJuso(address);
        testBuilding1.setBuildingHumanCapacity(100);
        testBuilding1.setBuildingClassification(1);
        testBuilding1.setBuildingSpecification(1);
        testBuilding1.setUndergroundFloors(1);
        testBuilding1.setOvergroundFloors(1);
        testBuilding1.setTotalFloors(2);
        testBuilding1.setGFA(1000.0);


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

        testBuilding1.setCompositeFloors(List.of(new Floor[]{testBuilding1Composed1F, testBuilding1ComposedB1F}));

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

    @Test
    public void testLawExecuteTestBuilding1UpToBuildingLawOnly() {
        MockitoAnnotations.openMocks(this);

        // Given
        List<BuildingLawFields> candidateBuildingLaw = new ArrayList<>(List.of(new BuildingLawFields[]{this.Blaw1}));

        // Mock the behavior of the lawService
        when(lawService.getLawsWithApplicablePurpose(testBuilding1)).thenReturn(candidateBuildingLaw);
        when(lawService.getLawsWithApplicablePurpose(any(Floor.class))).thenReturn(new LinkedList<>());

        // Act
        ResultSheet resultSheet = lawExecutionFacadeService.executeLaw(testBuilding1);

        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(resultSheet.getFloorResultsList())
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
                .containsOnly(true);

    }

    @Test
    public void testLawExecuteTestBuilding1Full() {
        MockitoAnnotations.openMocks(this);

        // Given
        List<BuildingLawFields> candidateBuildingLaw = new ArrayList<>(List.of(new BuildingLawFields[]{this.Blaw1}));
        List<FloorLawFields> candidateFloorLaw = new ArrayList<>(List.of(new FloorLawFields[]{this.Flaw1}));

        // Mock the behavior of the lawService
        when(lawService.getLawsWithApplicablePurpose(testBuilding1)).thenReturn(candidateBuildingLaw);
        when(lawService.getLawsWithApplicablePurpose(any(Floor.class))).thenReturn(candidateFloorLaw);

        // Act
        ResultSheet resultSheet = lawExecutionFacadeService.executeLaw(testBuilding1);

        log.info("resultSheet : {}", resultSheet);
        assertThat(resultSheet).isNotNull();
        assertThat(resultSheet.getFloorResultsList())
                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
                .containsOnly(true);

        assertThat(resultSheet.getFloorResultsList().get(0).getAlarmDeviceInstallation().getAutoFireDectionApparatus())
                .isEqualTo(true);

//        to be null or to be false -> to ask.
//        this really depends on being fully confident that all cases are covered
//        if all cases are covered, those that are not shown as true can be denoted false.
//        If that is not the case, can only guarantee those that are affected ==> show true values only.
        assertThat(resultSheet.getFloorResultsList().get(1).getAlarmDeviceInstallation().getAutoFireDectionApparatus())
                .isEqualTo(null);

    }

    @Test
    public void RandomGeneratedBuildingTest() {
        BuildingAttributes ba = BuildingAttributes.automatedBuild();
        Building building = buildingGenerator.buildingObjectBuilder(ba);
        log.info("Building {}, ", building);
    }

    @Test
    public void RandomGeneratedLawTest() {
        Building
    }


}
