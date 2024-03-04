//package com.FireFacilAuto.FireFacilAuto.service.lawservice;
//
//import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building.BuildingAttributes;
//import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building.TestBuildingObjectBuilder;
//import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building.TestFloorObjectBuilder;
//import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law.TestBuildingLawObjectBuilder;
//import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law.TestFloorLawObjectBuilder;
//import com.FireFacilAuto.domain.Conditions;
//import com.FireFacilAuto.domain.entity.building.Building;
//import com.FireFacilAuto.domain.entity.floors.Floor;
//import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
//import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
//import com.FireFacilAuto.domain.entity.results.ResultSheet;
//import com.FireFacilAuto.service.lawService.BuildingAndFloorLawExecutionFacadeService;
//import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawFormToFieldParser;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.query.sqm.ComparisonOperator;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@Slf4j
//public class BuildingLawFacadeServiceTest {
//    @InjectMocks
//    private final BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService;
//
//    @MockBean
//    private BuildingLawFormToFieldParser lawService;
//
//
//    private final TestBuildingObjectBuilder buildingGenerator = new TestBuildingObjectBuilder();
//    private final TestFloorObjectBuilder floorObjectBuilder = new TestFloorObjectBuilder();
//    private final TestBuildingLawObjectBuilder buildingLawObjectBuilder = new TestBuildingLawObjectBuilder();
//    private final TestFloorLawObjectBuilder floorLawObjectBuilder = new TestFloorLawObjectBuilder();
//
//    public BuildingLawFacadeServiceTest(BuildingAndFloorLawExecutionFacadeService lawExecutionFacadeService) {
//        this.lawExecutionFacadeService = lawExecutionFacadeService;
//    }
//
//    @Test
//    public void testBuilding2Test() {
//        BuildingLawFields fireExtinguisherGFAMoreThan33 = buildingLawObjectBuilder.singularBuildingLawBuilder(-1,-1,
//                1,1,33,null,-1,-1,-1,-1, null);
//        List<Conditions> COList1 = List.of(buildingLawObjectBuilder.conditionBuilder(fireExtinguisherGFAMoreThan33,"GFA", ComparisonOperator.GREATER_THAN_OR_EQUAL));
//        fireExtinguisherGFAMoreThan33.setConditionsList(COList1);
//
//        BuildingLawFields AutoFireAlarmOgfloorsMTOE6 = buildingLawObjectBuilder.singularBuildingLawBuilder(-1,-1,2,1,-1,null,-1,-1,6,-1,null);
//        List<Conditions> COList2 = List.of(buildingLawObjectBuilder.conditionBuilder(AutoFireAlarmOgfloorsMTOE6, "overgroundFloors", ComparisonOperator.GREATER_THAN_OR_EQUAL));
//        AutoFireAlarmOgfloorsMTOE6.setConditionsList(COList2);
//
//        FloorLawFields areaSumClinicGreaterThan600 = floorLawObjectBuilder.singularFloorLawBuilder(2,3,1,6,-1,null,-1,-1.0,600,null,null);
//        List<Conditions> COList3 = List.of(floorLawObjectBuilder.conditionBuilder(areaSumClinicGreaterThan600,"floorAreaSum",ComparisonOperator.GREATER_THAN_OR_EQUAL));
//        areaSumClinicGreaterThan600.setConditionsList(COList3);
//
//        BuildingAttributes ba = new BuildingAttributes(100,2,3,1,1,6,1500, LocalDate.now(), true);
//        Building building = buildingGenerator.buildingObjectBuilder(ba);
//
//        List<Floor> floorList = List.of(floorObjectBuilder.generateSingleFloor(building,2,3,1,true,200,1),
//                floorObjectBuilder.generateSingleFloor(building,2,3,1,false,300,1),
//                floorObjectBuilder.generateSingleFloor(building,2,3,2,false,200,1),
//                floorObjectBuilder.generateSingleFloor(building,2,3,3,false,200,1),
//                floorObjectBuilder.generateSingleFloor(building,2,1,4,false,200,1),
//                floorObjectBuilder.generateSingleFloor(building,2,1,5,false,200,1),
//                floorObjectBuilder.generateSingleFloor(building,2,1,6,false,200,1)
//        );
//
//        building.setCompositeFloorsList(floorList);
//
//        MockitoAnnotations.openMocks(this);
//
//        // Given
//        List<BuildingLawFields> candidateBuildingLaw = new ArrayList<>(List.of(new BuildingLawFields[]{fireExtinguisherGFAMoreThan33, AutoFireAlarmOgfloorsMTOE6}));
//        List<FloorLawFields> candidateFloorLaw = new ArrayList<>(List.of(new FloorLawFields[]{areaSumClinicGreaterThan600}));
//
//        // Mock the behavior of the lawService -> getLaws
//        when(lawService.getLawsWithApplicablePurpose(building)).thenReturn(candidateBuildingLaw);
//        for (int i = 0; i < 4; i++) {
//            when(lawService.getLawsWithApplicablePurpose(floorList.get(i))).thenReturn(candidateFloorLaw);
//        }
//        // Act
//        ResultSheet resultSheet = lawExecutionFacadeService.executeLaw(building);
//
//        log.info("resultSheet : {}", resultSheet);
////        resultsheet not null
//        assertThat(resultSheet).isNotNull();
////        all resultsheet fireExtinguisherInstall true
//        assertThat(resultSheet.getFloorResultsList())
//                .extracting(floorResults -> floorResults.getExtinguisherInstallation().getExtinguisherApparatus())
//                .containsOnly(true);
////      all alarmDevice autofiredetector true
//        assertThat(resultSheet.getFloorResultsList())
//                .extracting(floorResults -> floorResults.getAlarmDeviceInstallation().getAutoFireDectionApparatus())
//                .containsOnly(true);
//
////        only floors B1 - 3 has sprinkler
//        for (int i = 0; i < 4; i++) {
//            assertThat(resultSheet.getFloorResultsList().get(i).getExtinguisherInstallation().getSprinklerApparatus())
//                    .isEqualTo(true);
//        }
////      4 onwards has null -> no need to install
//        assertThat(resultSheet.getFloorResultsList().get(4).getExtinguisherInstallation().getSprinklerApparatus())
//                .isNull();
//
//    }
//}
