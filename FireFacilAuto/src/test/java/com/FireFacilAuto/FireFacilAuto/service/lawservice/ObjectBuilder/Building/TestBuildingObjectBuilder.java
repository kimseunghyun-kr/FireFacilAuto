package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestBuildingObjectBuilder {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final TestFloorObjectBuilder fb = new TestFloorObjectBuilder();

    /**
     * @return Random attributes for a building
     */
    private BuildingAttributes generateRandomBuildingAttributes() {
        BuildingAttributes attributes = new BuildingAttributes();

        attributes.bhc = random.nextInt(1000);
        attributes.bc = random.nextInt(1, 13);
        attributes.bs = random.nextInt(1, 4);
        attributes.ugf = random.nextInt(0, 4);
        attributes.ogf = random.nextInt(1, 10);
        attributes.tf = attributes.ugf + attributes.ogf;
        attributes.gfa = random.nextDouble(1, 10000);

        // Date logic
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        attributes.localdate = startDate.plusDays(randomDays);

        return attributes;
    }

    /**
     * @return a sample Building Object to be called for testing
     */
    public Building buildingObjectBuilder(BuildingAttributes buildingAttributes) {
        BuildingAttributes attributes;
        if (buildingAttributes.manaualBuildFlag) {
            attributes = buildingAttributes;
        } else {
            attributes = generateRandomBuildingAttributes();
        }

        Building building = new Building();
        Address address = Mockito.mock(Address.class);
        List<Floor> floors;

        // Set building attributes
        building.setJuso(address);
        building.setBuildingHumanCapacity(attributes.bhc);
        building.setBuildingClassification(attributes.bc);
        building.setBuildingSpecification(attributes.bs);
        building.setBuildingMaterial(attributes.bm);
        building.setUndergroundFloors(attributes.ugf);
        building.setOvergroundFloors(attributes.ogf);
        building.setTotalFloors(attributes.tf);
        building.setGFA(attributes.gfa);
        building.setDateofApproval(attributes.localdate);

        if(!buildingAttributes.manaualBuildFlag) {
            // Generate floors and set them in the building
            floors = fb.RandomInputFloorObjectsBuilder(building);
            building.setCompositeFloors(floors);
        }

        return building;
    }


}
