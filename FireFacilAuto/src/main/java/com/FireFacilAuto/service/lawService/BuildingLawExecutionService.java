package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.*;

@Service
@Slf4j
public class BuildingLawExecutionService {
    private final LawService lawService;
    private final ApiObjectToLawApplicableEntityService apiObjectConverter;

    @Autowired
    public BuildingLawExecutionService(LawService lawService, ApiObjectToLawApplicableEntityService apiObjectConverter) {
        this.lawService = lawService;
        this.apiObjectConverter = apiObjectConverter;
    }

    public ResultSheet executeLaw(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(building);

        List<FloorResults> floorResultsList = floorResultSheetBuilder(building);
        log.info("executing building laws");
        buildingLawExecute(building, floorResultsList);

        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaw = new LinkedList<>();

        log.info("receiving all appicable candidate for floor laws," +
                " floors may have differing classification and specification from building");
        floorLawCandidacyResolver(floorResultsList, floorResultStore, candidateFloorLaw);

        log.info("applying floor laws");
        floorLawExecute(building, floorResultsList, candidateFloorLaw);

        resultSheet.setFloorResultsList(floorResultsList);

        return resultSheet;
    }

    private void floorLawExecute(Building building, List<FloorResults> floorResultsList, List<FloorLawFields> candidateFloorLaw) {
        candidateFloorLaw.forEach(flf -> floorConditionComparator(flf, new LinkedList<>(floorResultsList), building));
    }

    private List<FloorResults> floorResultSheetBuilder(Building building) {
        return building.getCompositeFloors().stream().map(FloorResults::floorFactory).toList();
    }

    private ResultSheet resultSheetInitializr(Building building) {
        ResultSheet resultSheet = new ResultSheet();
        resultSheet.setBuilding(building);
        return resultSheet;
    }

    private void floorLawCandidacyResolver(List<FloorResults> floorResultsList, Set<Pair> floorResultStore, List<FloorLawFields> candidateFloorLaw) {
        for (FloorResults floorResults : floorResultsList) {
            Floor floor = floorResults.getFloor();
            Pair p = new Pair(floor.floorClassification, floor.floorClassification);
            if (floorResultStore.add(p)) {
                candidateFloorLaw.addAll(lawService.getLawsWithApplicablePurpose(floor));
            }
        }
    }

    private void buildingLawExecute(Building building, List<FloorResults> floorResultsList) {
        log.info("initializing candidate laws");
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);
        log.info("candidate law fields building : {}", candidateBuildingLaw);

        log.info("applying candidate laws onto building");
//        FOR ALL CANDIDATE BLFS APPLY EACH BLF ONTO BUILDING.
//        FloorResultList modified based on the building parameters
        candidateBuildingLaw.forEach(blf -> buildingConditionComparator(blf, building, floorResultsList));

        log.info("building laws applied");
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
                floorResultsList.removeIf(survivingResults -> !conditionParser(conditional.getOperator(), flf.floorNo, survivingResults.getFloor().floorNo));
            }

        }
        if (floorResultsList.isEmpty()) {
            return;
        }

//      Surviving inputs are all satisfyign the conditonal requirement of floorNo
//      floorAreaSum
        if (isActivated(flf.floorAreaSum)) {
            Conditions conditional = getCondition(conditions, "floorAreaSum");
            double floorAreaSum = calculateFloorAreaSum(floorResultsList, flf);
            if (!conditionParser(conditional.getOperator(), flf.floorAreaSum, floorAreaSum)) {
                floorResultsList.clear();
            }
        }
        if (floorResultsList.isEmpty()) {
            return;
        }
//      Surviving inputs are all satisfying the conditonal requirement of floorNo
//      floorAreaThreshold
        if (isActivated(flf.floorAreaThreshold)) {
            Conditions conditional = getCondition(conditions, "floorAreaThreshold");
            floorResultsList.removeIf(survivingResults -> !conditionParser(conditional.getOperator(), flf.floorAreaThreshold, survivingResults.getFloor().getFloorArea()));
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
        if(isActivated(flf.floorWindowAvailability)) {
            floorResultsList.removeIf(survivingResults -> survivingResults.getFloor().getFloorWindowAvailability() != flf.getFloorWindowAvailability());
        }

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
        log.info("class : {}, spec : {}", target[0], target[1]);
        for (FloorResults survivingResults : floorResultsList) {
            survivingResults.numericSetter(target[0], target[1]);
        }
    }
    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};

//        FOR A SINGLE(THE) BLF, IF ALL MATCH THEN LABEL ALL FLOORS TRUE
        boolean isTrue = true;
        isTrue &= compareField(blf, building, Building::getGFA, blf.GFA, "GFA");
        isTrue &= compareField(blf, building, Building::getDateofApproval, blf.dateofApproval, "dateofApproval");
        isTrue &= compareField(blf, building, Building::getBuildingHumanCapacity, blf.buildingHumanCapacity, "buildingHumanCapacity");
        isTrue &= compareField(blf, building, Building::getLength, blf.length, "length");
        isTrue &= compareField(blf, building, Building::getTotalFloors, blf.totalFloors, "totalFloors");
        isTrue &= compareField(blf, building, Building::getUndergroundFloors, blf.undergroundFloors, "undergroundFloors");
        isTrue &= compareField(blf, building, Building::getOvergroundFloors, blf.overgroundFloors, "overgroundFloors");

        log.info("BLF PASSING ?= {} ", isTrue);
        if (isTrue) {
            floorResultListMajorCodeMapper(floorResultsList, target);
        }
    }


    private <T extends Comparable<T>> boolean compareField(BuildingLawFields blf, Building building,
                                                           Function<Building, T> buildingFieldExtractor, T value ,String fieldName) {

        log.info("blf fieldName : {} , corresponding value  : {}", fieldName , value);
        log.info("building value : {}", buildingFieldExtractor.apply(building));
        if (isActivated(value)) {
            Conditions condition = blf.conditionsList.stream()
                    .filter(c -> c.getFieldName().equals(fieldName))
                    .findFirst()
                    .orElseThrow();
            return conditionParser(condition.getOperator(), value, buildingFieldExtractor.apply(building));
        }
        return true;
    }

    private Conditions getCondition(List<Conditions> conditions, String fieldName) {
        return conditions.stream()
                .filter(c -> c.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow();
    }

    public ResultSheet buildingBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems) {
        Building building = new Building();
        apiObjectConverter.buildingInitializr(address, titleResponseItem, building);

        List<Floor> floors = floorResponseItemListToFloorListConverter(titleResponseItem, floorResponseItems, building);
        building.setCompositeFloors(floors);

        return executeLaw(building);
    }



    private List<Floor> floorResponseItemListToFloorListConverter(TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems, Building building) {
        return floorResponseItems.stream().map(floorResponseItem -> {
            Floor floor = new Floor();
            floor.setBuilding(building);

            //TODO -> allocate number for classification based on code
            //Classification, specififation;

            apiObjectConverter.floorInitializr(titleResponseItem,null, floorResponseItem, floor);
            return floor;
        }).toList();
    }



    public ResultSheet floorBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem,
                                               ExposedInfoResponseItem exposInfoResponseItem,
                                               FloorResponseItem floorResponseItem) {
        Building building = new Building();
        apiObjectConverter.buildingInitializr(address, titleResponseItem, building);
        Floor floor = new Floor();
        apiObjectConverter.floorInitializr(titleResponseItem, exposInfoResponseItem, floorResponseItem, floor);
        building.setCompositeFloors(List.of(floor));
        return executeLaw(building);
    }


}
