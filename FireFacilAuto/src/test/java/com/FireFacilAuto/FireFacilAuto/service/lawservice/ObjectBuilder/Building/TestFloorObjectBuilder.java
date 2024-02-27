package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestFloorObjectBuilder {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    /**
     * @param building: sets the building where the floorClassSpecBias die would apply to
     * @param flag: set 1 for class, 2 for spec, 3 for material
     * @return Integer result of a biased die for the creation of test Floors' classification and specification
     */
    public Integer floorClassSpecBias(Building building, Integer flag) {
        Integer biasTo = switch (flag) {
            case 1 -> building.getBuildingClassification();
            case 2 -> building.getBuildingSpecification();
            case 3 -> building.getBuildingMaterial();
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
        Integer totalToGenerate = building.totalFloors;
        Integer undergroundFloors = building.getUndergroundFloors();
        Integer overGroundFloors = building.getOvergroundFloors();
        Double availableGroundLimit = building.getGFA();

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
            Floor floor = new Floor();
            floor.setBuilding(building);
            floor.setFloorNo(i);
            floor.setIsUnderGround(isUnderGround);
            floor.setFloorClassification(floorClassSpecBias(building, 1));
            floor.setFloorSpecification(floorClassSpecBias(building, 2));
            floor.setFloorArea(random.nextDouble(1, availableGroundLimit / 2));
            availableGroundLimit -= floor.getFloorArea();
            floor.setFloorWindowAvailability(!isUnderGround && random.nextBoolean());
            floor.setFloorMaterial(floorClassSpecBias(building, 3));

            floors.add(floor);
        }
    }


    /**
     * @param building           : building the floors are part of
     * @param floorNo            : floor number
     * @param isUnderGround      : flag to set if building is underground
     * @param floorArea: available floor area limit
     * @return: a single floor object with specified parameters
     */
    public Floor generateSingleFloor(Building building, int classification, int specification, int floorNo, boolean isUnderGround, double floorArea, int floorMaterial) {
        Floor floor = new Floor();
        floor.setBuilding(building);
        floor.setFloorNo(floorNo);
        floor.setIsUnderGround(isUnderGround);
        floor.setFloorClassification(classification);
        floor.setFloorSpecification(specification);
        floor.setFloorArea(floorArea);
        floor.setFloorWindowAvailability(!isUnderGround && random.nextBoolean());
        floor.setFloorMaterial(floorMaterial);

        return floor;
    }

}
