package com.FireFacilAuto.service.lawService;

public class BuildingLawExecutionServiceDeprecatedMethodStore {
    /*
    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        // Compare activated values for specific fields
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};
        List<Conditions> conditions = blf.conditionsList;
        boolean isTrue = true;
        if(isActivated(blf.GFA)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("GFA"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.GFA, building.GFA);
        }
        if(isActivated(blf.dateofApproval)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("dateofApproval"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.dateofApproval, building.dateofApproval);
        }
        if(isActivated(blf.buildingHumanCapacity)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("buildingHumanCapacity"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.buildingHumanCapacity, building.buildingHumanCapacity);
        }
        if(isActivated(blf.length)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("length"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.length, building.length);
        }
        if(isActivated(blf.totalFloors)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("totalFloors"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.totalFloors, building.totalFloors);
        }
        if(isActivated(blf.undergroundFloors)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("undergroundFloors"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.undergroundFloors, building.undergroundFloors);
        }
        if(isActivated(blf.overgroundFloors)) {
            Conditions gfaCondition = conditions.stream()
                    .filter(condition -> condition.getFieldName().equals("overgroundFloors"))
                    .findFirst().orElseThrow();
            isTrue = conditionParser(gfaCondition.getOperator(), blf.overgroundFloors, building.overgroundFloors);
        }

        if(isTrue) {
            for(FloorResults floorResults : floorResultsList) {
                switch(target[0]) {
                    case 1: floorResults.getExtinguisherInstallation().setBooleanValue(target[1]);
                    case 2: floorResults.getAlarmDeviceInstallation().setBooleanValue(target[1]);
                    case 3: floorResults.getFireServiceSupportDeviceInstallation().setBooleanValue(target[1]);
                    case 4: floorResults.getWaterSupplyInstallation().setBooleanValue(target[1]);
                    case 5: floorResults.getEscapeRescueInstallation().setBooleanValue(target[1]);
                }
            }
        }

//        blf.GFA;
//        blf.dateofApproval;
//        blf.buildingHumanCapacity;
//        blf.length;
//        blf.totalFloors;
//        blf.undergroundFloors;
//        blf.overgroundFloors;


    }
 */
}
