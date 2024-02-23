package com.FireFacilAuto.FireFacilAuto.service.lawservice;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.installation.*;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.service.lawService.BuildingLawExecutionService;
import com.FireFacilAuto.service.lawService.LawService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BuildingLawExecutionServiceTest {
    private final BuildingLawExecutionService buildingLawExecutionService;
    private Building building;
    @Mock
    private LawService lawService;


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

    @Test
    public void testBuildingLawExecute() {
        // Arrange
        Building building = mock(Building.class);  // Mock a Building instance
        List<FloorResults> floorResultsList = new ArrayList<>();  // Initialize floorResultsList as needed
        List<BuildingLawFields> candidateBuildingLaw = new ArrayList<>();  // Initialize candidateBuildingLaw as needed

        // Mock the behavior of the lawService
        when(lawService.getLawsWithApplicablePurpose(building)).thenReturn(candidateBuildingLaw);

        // Mock a BuildingLawFields instance
        BuildingLawFields buildingLawFields = mock(BuildingLawFields.class);
        when(buildingLawFields.getTotalFloors()).thenReturn(3);  // Set up specific behavior as needed

        // Act
//        buildingLawExecutionService.buildingLawExecute(building, floorResultsList);

        // Assert

        // Verify that laws are retrieved from lawService
        verify(lawService, times(1)).getLawsWithApplicablePurpose(building);

        // Verify that buildingConditionComparator is called for each candidate law
        for (BuildingLawFields blf : candidateBuildingLaw) {
//            verify(yourClass, times(1)).buildingConditionComparator(blf, building, floorResultsList);
        }

        // Add assertions based on the expected behavior of buildingLawExecute
        // For example, check that floorResultsList has been modified as expected
    }


}
