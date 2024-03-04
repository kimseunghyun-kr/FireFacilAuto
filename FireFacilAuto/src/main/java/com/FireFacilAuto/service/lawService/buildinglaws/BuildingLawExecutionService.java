package com.FireFacilAuto.service.lawService.buildinglaws;

import com.FireFacilAuto.domain.entity.building.Building;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.FireFacilAuto.domain.entity.lawfields.LawUtils.clausePrioritysort;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;

@Service
@Slf4j
public class BuildingLawExecutionService {
    private final BuildingLawRepositoryService lawService;

    @Autowired
    public BuildingLawExecutionService(BuildingLawRepositoryService lawService) {
        this.lawService = lawService;
    }


    public void buildingLawExecute(Building building, List<FloorResults> floorResultsList) {
        log.info("initializing candidate laws");
        List<BuildingLawFields> candidateBuildingLaw = lawService.getLawsWithApplicablePurpose(building);
        log.info("candidate law fields building : {}", candidateBuildingLaw);
        log.info("applying candidate laws onto building");

//        FOR ALL CANDIDATE BLFS APPLY EACH BLF ONTO BUILDING.
//        FloorResultList modified based on the building parameters
        candidateBuildingLaw.forEach(blf -> {
            clausePrioritysort(blf.clauses);
            buildingConditionComparator(blf, building, floorResultsList);
        });

        log.info("building laws applied");
    }


    private void buildingConditionComparator(BuildingLawFields blf, Building building, List<FloorResults> floorResultsList) {
        Integer[] target = {blf.majorCategoryCode, blf.minorCategoryCode};

        if(blf.buildingClassification != -1) {
            Clause<Integer> classificationClause = ClauseFactory.createClause("buildingClassification", PossibleBuildingClauses.class, ComparisonOperator.EQUAL, blf.buildingClassification, 1);
            if (blf.buildingSpecification != -1) {
                Clause<Integer> specificationClause = Clause.createClause("buildingSpecification",PossibleBuildingClauses.class, ComparisonOperator.EQUAL, blf.buildingSpecification, 1);
                blf.clauses.addFirst(specificationClause);
            }
            blf.clauses.addFirst(classificationClause);
        }


//        FOR A SINGLE(THE) BLF, IF ALL MATCH THEN LABEL ALL FLOORS TRUE
        boolean isTrue = true;
        for (Clause<?> clause : blf.clauses) {
            isTrue &= ClauseEvaluator.evaluateSingleBuilding(clause, building);
        }
        log.info("BLF PASSING ?= {} ", isTrue);
        if (isTrue) {
            floorResultListMajorCodeMapper(floorResultsList, target);
        }
    }

}
