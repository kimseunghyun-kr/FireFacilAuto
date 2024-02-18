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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.*;

@Service
@Slf4j
public class BuildingLawExecutionService {
    private final LawService lawService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

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

        log.info("part 3");
//        building setup
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);
        candidateBuildingLaw.forEach(blf -> buildingConditionComparator(blf, building, floorResultsList));
        log.info("part 4");
//        해당되는 법령 받아오기
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaw = new LinkedList<>();

        log.info("part 5");
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
        if (isActivated(flf.floorAreaSum)) {
            Conditions conditional = getCondition(conditions, "floorArea");
            double floorAreaSum = calculateFloorAreaSum(floorResultsList, flf);
            if (!conditionParser(conditional.getOperator(), floorAreaSum, flf.floorAreaSum)) {
                floorResultsList.clear();
            }
        }
        if (floorResultsList.isEmpty()) {
            return;
        }
//      Surviving inputs are all satisfyign the conditonal requirement of floorNo
//      floorAreaThreshold
        if (isActivated(flf.floorAreaThreshold)) {
            Conditions conditional = getCondition(conditions, "floorThreshold");
            floorResultsList.removeIf(survivingResults -> !conditionParser(conditional.getOperator(), survivingResults.getFloor().getFloorArea(), flf.floorAreaThreshold));
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
                case 2 -> survivingResults.getAlarmDeviceInstallation().setBooleanValues(target[1]);
                case 3 -> survivingResults.getFireServiceSupportDeviceInstallation().setBooleanValue(target[1]);
                case 4 -> survivingResults.getWaterSupplyInstallation().setBooleanValue(target[1]);
                case 5 -> survivingResults.getEscapeRescueInstallation().setBooleanValue(target[1]);
            }
        }
    }
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

    public ResultSheet buildingBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems) {
        Building building = new Building();
        buildingInitializr(address, titleResponseItem, building);

        List<Floor> floors = floorResponseItemListToFloorListConverter(titleResponseItem, floorResponseItems, building);
        building.setCompositeFloors(floors);

        return executeLaw(building);
    }

    private void buildingInitializr(Address address, TitleResponseItem titleResponseItem, Building building) {
        //TODO -> allocate number for classification based on code
        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem);

        building.setBuildingClassification(classificationCodeMapper(titleResponseItem));
        building.setBuildingSpecification(specificationCodeMapper(titleResponseItem));

        building.setJuso(address);

        log.info("titleResponseItem permissionNoGbcd {}", titleResponseItem.getPmsDay());
        building.setDateofApproval(titleResponseItem.getPmsDay().isEmpty() ? LocalDate.now() :LocalDate.parse(titleResponseItem.getPmsDay(),formatter));
        building.setGFA(Double.valueOf(titleResponseItem.getTotArea()));
        building.setUndergroundFloors(Integer.valueOf(titleResponseItem.getUgrndFlrCnt()));
        building.setOvergroundFloors(Integer.valueOf(titleResponseItem.getGrndFlrCnt()));
        building.setTotalFloors(building.overgroundFloors + building.undergroundFloors);
        //TODO -> calculate capacity
        building.setBuildingHumanCapacity(getBuildingCapacity(titleResponseItem));
    }


    private List<Floor> floorResponseItemListToFloorListConverter(TitleResponseItem titleResponseItem, List<FloorResponseItem> floorResponseItems, Building building) {
        return floorResponseItems.stream().map(floorResponseItem -> {
            Floor floor = new Floor();
            floor.setBuilding(building);

            //TODO -> allocate number for classification based on code
            //Classification, specififation;
            floor.setFloorClassification(classificationCodeMapper(floorResponseItem, titleResponseItem));
            floor.setFloorSpecification(specificationCodeMapper(floorResponseItem, titleResponseItem));

            floor.setFloorMaterial(Integer.valueOf(floorResponseItem.getStrctCd()));
            floor.setIsUnderGround(floorGbCdMapper(floorResponseItem.getFlrGbCd()));
            floor.setFloorArea(Double.valueOf(floorResponseItem.getArea()));
            floor.setFloorNo(Integer.valueOf(floorResponseItem.getFlrNo()));

            return floor;
        }).toList();
    }

    private Boolean floorGbCdMapper(String flrGbCd) {
        int target = Integer.parseInt(flrGbCd);
        if(target == 20) {
            return false;
        } else if (target == 10) {
            return true;
        }
        throw new RuntimeException("Invalid Mapping");
    }

    private Integer getBuildingCapacity(TitleResponseItem titleResponseItem) {
        return Integer.valueOf(titleResponseItem.getHhldCnt());
    }

    //TODO
    private Integer classificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1;
    }
    //TODO
    private Integer classificationCodeMapper(TitleResponseItem title) {
        return 1;
    }

    //TODO
    private Integer specificationCodeMapper(FloorResponseItem floor, TitleResponseItem title) {
        return 1;
    }
    //TODO
    private Integer specificationCodeMapper(TitleResponseItem title) {
        return 1;
    }

    public ResultSheet floorBuildAndExecuteLaw(Address address, TitleResponseItem titleResponseItem,
                                               ExposedInfoResponseItem exposInfoResponseItem,
                                               FloorResponseItem floorResponseItem) {
        Building building = new Building();
        buildingInitializr(address, titleResponseItem, building);

        Floor floor = new Floor();
        //Todo ;
        floor.setFloorClassification(classificationCodeMapper(floorResponseItem, titleResponseItem));
        floor.setFloorSpecification(specificationCodeMapper(floorResponseItem, titleResponseItem));

        floor.setFloorMaterial(Integer.valueOf(floorResponseItem.getStrctCd()));
        floor.setIsUnderGround(floorGbCdMapper(exposInfoResponseItem.getFlrGbCd()));
        floor.setFloorArea(Double.valueOf(floorResponseItem.getArea()));
        floor.setFloorNo(Integer.valueOf(exposInfoResponseItem.getFlrNo()));

        building.setCompositeFloors(List.of(floor));
        return executeLaw(building);
    }
}
