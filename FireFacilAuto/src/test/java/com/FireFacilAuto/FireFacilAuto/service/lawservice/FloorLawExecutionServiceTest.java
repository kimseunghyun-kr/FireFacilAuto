package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building.TestFloorObjectBuilder;
import com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law.TestFloorLawObjectBuilder;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.buildinglaws.BuildingLawFormToFieldParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class FloorLawExecutionServiceTest {
    private final TestFloorObjectBuilder floorObjectBuilder = new TestFloorObjectBuilder();
    private final TestFloorLawObjectBuilder floorLawObjectBuilder = new TestFloorLawObjectBuilder();
    @InjectMocks
    private final BuildingLawExecutionService lawExecutionService;

    @MockBean
    private BuildingLawFormToFieldParser lawService;

    public FloorLawExecutionServiceTest(BuildingLawExecutionService lawExecutionService) {
        this.lawExecutionService = lawExecutionService;
    }

    @Test
    public void manuallyMadeBuildingAndFloorLawTest() {
        MockitoAnnotations.openMocks(this);

        Building testBuilding1 = Mockito.mock(Building.class);
        List<Floor> floors = List.of(floorObjectBuilder.generateSingleFloor(testBuilding1, 1,1,1,false,100.0,1));
        when(testBuilding1.getCompositeFloors()).thenReturn(floors);

        FloorLawFields floorNo11Upand30Down = floorLawObjectBuilder.singularFloorLawBuilder(-1,-1,1,6,)
        // Given
        List<FloorLawFields> candidateFloorLaw = new ArrayList<>(List.of(new FloorLawFields[]{this.Flaw1}));

        // Mock the behavior of the lawService
        when(lawService.getLawsWithApplicablePurpose(testBuilding1)).thenReturn(candidateBuildingLaw);
        when(lawService.getLawsWithApplicablePurpose(any(Floor.class))).thenReturn(candidateFloorLaw);

        // Act
        ResultSheet resultSheet = lawExecutionService.executeLaw(testBuilding1);

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


}
