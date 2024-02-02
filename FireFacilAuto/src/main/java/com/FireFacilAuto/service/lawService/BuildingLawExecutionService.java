package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.util.records.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.conditionParser;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Service
public class BuildingLawExecutionService {
    private final LawService lawService;

    @Autowired
    public BuildingLawExecutionService(LawService lawService) {
        this.lawService = lawService;
    }

    public ResultSheet executeLaw(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }

        ResultSheet resultSheet = new ResultSheet();
        resultSheet.setBuilding(building);
        List<FloorResults> floorResultsList = building.getCompositeFloors().stream().map(FloorResults::floorFactory).toList();
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);

        for (BuildingLawFields blf : candidateBuildingLaw) {
            buildingConditionComparator(blf, building, floorResultsList);
        }

//        해당되는 법령 받아오기
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaw = new LinkedList<>();
//        Map<Integer, Long> classificationFloorArea = new HashMap<>();
//        Map<Pair, Long> specificationFloorArea = new HashMap<>();
        for(FloorResults floorresults : floorResultsList) {
            Floor floor = floorresults.getFloor();
            Pair p = new Pair(floor.floorClassification, floor.floorClassification);
            if(floorResultStore.contains(p)) {
                continue;
            }
            candidateFloorLaw.addAll(lawService.getLawsWithApplicablePurpose(floor));
            floorResultStore.add(p);
        }

        for(FloorLawFields flf : candidateFloorLaw) {
            floorConditionComparator(flf, floorResultsList);
        }

        return resultSheet;

    }

    private void floorConditionComparator(FloorLawFields flf, List<FloorResults> floorResultsList) {

    }


    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};

        boolean isTrue = true;
        isTrue &= compareField(blf, building, floorResultsList, Building::getGFA, blf.GFA);
        isTrue &= compareField(blf, building, floorResultsList, Building::getDateofApproval, blf.dateofApproval);
        isTrue &= compareField(blf, building, floorResultsList, Building::getBuildingHumanCapacity, blf.buildingHumanCapacity);
        isTrue &= compareField(blf, building, floorResultsList, Building::getLength, blf.length);
        isTrue &= compareField(blf, building, floorResultsList, Building::getTotalFloors, blf.totalFloors);
        isTrue &= compareField(blf, building, floorResultsList, Building::getUndergroundFloors, blf.undergroundFloors);
        isTrue &= compareField(blf, building, floorResultsList, Building::getOvergroundFloors, blf.overgroundFloors);

        if (isTrue) {
            for (FloorResults floorResults : floorResultsList) {
                switch (target[0]) {
                    case 1 -> floorResults.getExtinguisherInstallation().setBooleanValue(target[1]);
                    case 2 -> floorResults.getAlarmDeviceInstallation().setBooleanValue(target[1]);
                    case 3 -> floorResults.getFireServiceSupportDeviceInstallation().setBooleanValue(target[1]);
                    case 4 -> floorResults.getWaterSupplyInstallation().setBooleanValue(target[1]);
                    case 5 -> floorResults.getEscapeRescueInstallation().setBooleanValue(target[1]);
                }
            }
        }
    }

    private <T extends Comparable<T>> boolean compareField(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList,
                                     Function<Building, T> buildingFieldExtractor, T fieldValue) {
        if (isActivated(fieldValue)) {
            T fieldName = buildingFieldExtractor.apply(building);
            Conditions condition = blf.conditionsList.stream()
                    .filter(c -> c.getFieldName().equals(fieldName))
                    .findFirst()
                    .orElseThrow();
            return conditionParser(condition.getOperator(), fieldValue, buildingFieldExtractor.apply(building));
        }
        return true;
    }




}




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
