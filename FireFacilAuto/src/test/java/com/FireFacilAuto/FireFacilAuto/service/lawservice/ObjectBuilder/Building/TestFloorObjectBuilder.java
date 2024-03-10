package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.floors.FloorAttributes;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.FireFacilAuto.domain.entity.building.BuildingUtils.*;

public class TestFloorObjectBuilder {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * @param building: sets the building where the floorClassSpecBias die would apply to
     * @param flag: set 1 for class, 2 for spec, 3 for material
     * @return Integer result of a biased die for the creation of test Floors' classification and specification
     */
    public Integer floorClassSpecBias(Building building, Integer flag) {
        Integer biasTo = switch (flag) {
            case 1 -> getBuildingClassification(building);
            case 2 -> getBuildingSpecification(building);
            case 3 -> (Integer)getBuildingFieldByName(building, "buildingMaterial").getValue();
            default ->
                // Handle default case or set biasTo to some default value
                    null; // or another default value
        };

        double biasDie = random.nextDouble();

        if(biasDie < 0.2) {
            return random.nextInt(1,4);
        } else {
            return biasTo;
        }
    }

    /**
     *
     * @param building : building whose floors are to be built
     * @return : list of floor objects for the building
     */
    public List<Floor> RandomInputFloorObjectsBuilder(Building building) {
        List<Floor> floors = new LinkedList<>();
        Integer totalToGenerate = (Integer) getBuildingFieldValueByName(building, "totalFloors");
        Integer undergroundFloors = (Integer) getBuildingFieldValueByName(building, "undergroundFloors");
        Integer overGroundFloors = (Integer) getBuildingFieldValueByName(building, "overgroundFloors");
        Double availableGroundLimit = (Double) getBuildingFieldValueByName(building, "GFA");

        generateFloors(building, floors, undergroundFloors, true, availableGroundLimit);
        generateFloors(building, floors, overGroundFloors, false, availableGroundLimit);

        assert floors.size() == totalToGenerate;
        return floors;
    }

    /**
     * utility method to generate the floor details
     * @param building: building the floors are part of
     * @param floors: list of floors to be returned to caller method
     * @param floorCount: number of floors total in the building
     * @param isUnderGround: flag to set if building is underground
     * @param availableGroundLimit: available floor area limit
     */
    private void generateFloors(Building building, List<Floor> floors, int floorCount, boolean isUnderGround, double availableGroundLimit) {
        for (int i = 1; i <= floorCount; i++) {
            FloorAttributes.FloorBuilder fb = new FloorAttributes.FloorBuilder();
            Floor floor = fb
                    .building(building)
                    .floorNo(i)
                    .floorClassification(floorClassSpecBias(building, 1))
                    .floorSpecification(floorClassSpecBias(building, 2))
                    .floorMaterial(floorClassSpecBias(building, 3))
                    .floorArea(random.nextDouble(1, availableGroundLimit / 2))
                    .isUnderGround(isUnderGround)
                    .floorWindowAvailability(!isUnderGround && random.nextBoolean())
                    .build();

            floors.add(floor);
        }
    }

}
