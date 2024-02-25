package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.building.Building;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.getCondition;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.*;

@Service
@Slf4j
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

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(building);

        List<FloorResults> floorResultsList = floorResultSheetBuilder(building);
        log.info("executing building laws");
        buildingLawExecute(building, floorResultsList);



        return resultSheet;
    }



    private List<FloorResults> floorResultSheetBuilder(Building building) {
        return building.getCompositeFloors().stream().map(FloorResults::floorFactory).toList();
    }

    private ResultSheet resultSheetInitializr(Building building) {
        ResultSheet resultSheet = new ResultSheet();
        resultSheet.setBuilding(building);
        return resultSheet;
    }



    protected void buildingLawExecute(Building building, List<FloorResults> floorResultsList) {
        log.info("initializing candidate laws");
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);
        log.info("candidate law fields building : {}", candidateBuildingLaw);

        log.info("applying candidate laws onto building");
//        FOR ALL CANDIDATE BLFS APPLY EACH BLF ONTO BUILDING.
//        FloorResultList modified based on the building parameters
        candidateBuildingLaw.forEach(blf -> buildingConditionComparator(blf, building, floorResultsList));

        log.info("building laws applied");
    }


    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};

//        FOR A SINGLE(THE) BLF, IF ALL MATCH THEN LABEL ALL FLOORS TRUE
        boolean isTrue = true;
        isTrue &= compareField(blf, building, Building::getGFA, blf.GFA, "GFA");
        isTrue &= compareField(blf, building, Building::getDateofApproval, blf.dateofApproval, "dateofApproval");
        isTrue &= compareField(blf, building, Building::getBuildingHumanCapacity, blf.buildingHumanCapacity, "buildingHumanCapacity");
        isTrue &= compareField(blf, building, Building::getBuildingMaterial, blf.buildingMaterials, "buildingMaterials");
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
        List<Conditions> conditionsList = blf.conditionsList;
        if (isActivated(value)) {
            Conditions condition = getCondition(conditionsList, fieldName);
            return conditionParser(condition.getOperator(), value, buildingFieldExtractor.apply(building));
        }
        return true;
    }





}
