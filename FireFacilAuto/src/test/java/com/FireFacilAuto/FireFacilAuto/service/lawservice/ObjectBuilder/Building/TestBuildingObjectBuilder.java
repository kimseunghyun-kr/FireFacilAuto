package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingAttributes;
import com.FireFacilAuto.domain.entity.floors.Floor;
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
    private Building generateRandomBuildingAttributes() {    // Date logic
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

    BuildingAttributes.BuildingAttributesBuilder ba = BuildingAttributes.builder()
                .buildingClassification(random.nextInt(1, 13))
                .buildingSpecification(random.nextInt(1, 4))
                .buildingHumanCapacity(random.nextInt(1000))
                .buildingMaterial(random.nextInt(0, 4))
                .undergroundFloors(random.nextInt(0, 4))
                .overgroundFloors(random.nextInt(1, 10))
                .gfa(random.nextDouble(1, 10000))
                .approvalDate(startDate.plusDays(randomDays));


        return ba.build();
    }

    /**
     * @return a sample Building Object to be called for testing
     */
    public Building autoBuildingObjectBuilder() {
        Building building = generateRandomBuildingAttributes();
        Address address = Mockito.mock(Address.class);
        List<Floor> floors;

        floors = fb.RandomInputFloorObjectsBuilder(building);
        building.setCompositeFloorsList(floors);
        return building;
    }


}
