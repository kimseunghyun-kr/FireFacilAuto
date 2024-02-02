package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.util.records.Pair;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.*;

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

//        building setup
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);
        candidateBuildingLaw.forEach(blf -> buildingConditionComparator(blf, building, floorResultsList));

//        해당되는 법령 받아오기
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaw = new LinkedList<>();

        for (FloorResults floorResults : floorResultsList) {
            Floor floor = floorResults.getFloor();
            Pair p = new Pair(floor.floorClassification, floor.floorClassification);
            if (floorResultStore.add(p)) {
                candidateFloorLaw.addAll(lawService.getLawsWithApplicablePurpose(floor));
            }
        }

        candidateFloorLaw.forEach(flf -> floorConditionComparator(flf, new LinkedList<>(floorResultsList), building));

        resultSheet.setFloorResultsList(floorResultsList);

        return resultSheet;

    }

    private void floorConditionComparator(FloorLawFields flf, List<FloorResults> floorResultsList, Building building) {
        if (building == null || floorResultsList == null || floorResultsList.isEmpty()) {
            return;
        }

        Integer[] target = {flf.majorCategoryCode, flf.minorCategoryCode};
        List<Conditions> conditions = flf.conditionsList;

//      floorNo && isUnderground
        if (isActivated(flf.floorNo)) {
            Conditions conditional = getCondition(conditions, "floorNo");
            if (conditional.getOperator().equals(ComparisonOperator.GREATER_THAN) || conditional.getOperator().equals(ComparisonOperator.GREATER_THAN_OR_EQUAL)) {
                if ((flf.isUnderGround && building.undergroundFloors < flf.floorNo) || (!flf.isUnderGround && building.overgroundFloors < flf.floorNo)) {
                    return;
                }
                floorResultsList.removeIf(survivingResults -> !conditionParser(conditional.getOperator(), survivingResults.getFloor().floorNo, flf.floorNo));
            }

        }
        if (floorResultsList.isEmpty()) {
            return;
        }

//      Surviving inputs are all satisfyign the conditonal requirement of floorNo
//      floorAreaSum
        if (isActivated(flf.floorArea)) {
            Conditions conditional = getCondition(conditions, "floorArea");
            double floorAreaSum = calculateFloorAreaSum(floorResultsList, flf);
            if (!conditionParser(conditional.getOperator(), floorAreaSum, flf.floorArea)) {
                floorResultsList.clear();
            }
        }
        if (floorResultsList.isEmpty()) {
            return;
        }

//      surviving inputs has floor area sum of given classification, specifiation meeting condition
//      floorMaterial
        if (isActivated(flf.floorMaterial)) {
            floorResultsList.removeIf(survivingResults -> !Objects.equals(survivingResults.getFloor().getFloorMaterial(), flf.floorMaterial));
            if (floorResultsList.isEmpty()) {
                return;
            }
        }

//        surviving inputs have floor materials as designated by flf
//        floorWindowAvailability
        floorResultsList.removeIf(survivingResults -> survivingResults.getFloor().getFloorWindowAvailability() != flf.getFloorWindowAvailability());

        floorResultListMajorCodeMapper(floorResultsList, target);


//             Integer floorNo; //충수
//             Boolean isUnderGround; //지하여부
//             Integer floorClassification; //층 주용도
//             Integer floorSpecification; //층 세부용도
//             Double floorArea; //층 바닥면적
//             Integer floorMaterial; //층 재료
//             Boolean floorWindowAvailability; //무창층 (무창층에만 1 / 아닐 시 0)


    }

    private double calculateFloorAreaSum(List<FloorResults> floorResultsList, FloorLawFields flf) {
        double floorAreaSum = 0.0;
        for (FloorResults survivingResults : floorResultsList) {
            if (flf.floorClassification == -1 ||
                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification == -1) ||
                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification.equals(survivingResults.getFloor().getFloorSpecification()))) {
                floorAreaSum += survivingResults.getFloor().floorArea;
            } else {
                floorResultsList.remove(survivingResults);
            }
        }
        return floorAreaSum;
    }

    private void floorResultListMajorCodeMapper(List<FloorResults> floorResultsList, Integer[] target) {
        for (FloorResults survivingResults : floorResultsList) {
            switch (target[0]) {
                case 1 -> survivingResults.getExtinguisherInstallation().setBooleanValue(target[1]);
                case 2 -> survivingResults.getAlarmDeviceInstallation().setBooleanValue(target[1]);
                case 3 -> survivingResults.getFireServiceSupportDeviceInstallation().setBooleanValue(target[1]);
                case 4 -> survivingResults.getWaterSupplyInstallation().setBooleanValue(target[1]);
                case 5 -> survivingResults.getEscapeRescueInstallation().setBooleanValue(target[1]);
            }
        }
    }


    //        public boolean buildingFieldAssociableWithCondition(String fieldName) {
//        List<String> fieldsWithConditions = Arrays.asList("totalFloors", "undergroundFloors", "overgroundFloors",
//                "GFA", "length", "dateofApproval", "buildingHumanCapacity");
//        return fieldsWithConditions.contains(fieldName);
    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};

        boolean isTrue = true;
        isTrue &= compareField(blf, building, Building::getGFA, blf.GFA);
        isTrue &= compareField(blf, building, Building::getDateofApproval, blf.dateofApproval);
        isTrue &= compareField(blf, building, Building::getBuildingHumanCapacity, blf.buildingHumanCapacity);
        isTrue &= compareField(blf, building, Building::getLength, blf.length);
        isTrue &= compareField(blf, building, Building::getTotalFloors, blf.totalFloors);
        isTrue &= compareField(blf, building, Building::getUndergroundFloors, blf.undergroundFloors);
        isTrue &= compareField(blf, building, Building::getOvergroundFloors, blf.overgroundFloors);

        if (isTrue) {
            floorResultListMajorCodeMapper(floorResultsList, target);
        }
    }

    private <T extends Comparable<T>> boolean compareField(BuildingLawFields blf, Building building,
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

    private Conditions getCondition(List<Conditions> conditions, String fieldName) {
        return conditions.stream()
                .filter(c -> c.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow();
    }
}
