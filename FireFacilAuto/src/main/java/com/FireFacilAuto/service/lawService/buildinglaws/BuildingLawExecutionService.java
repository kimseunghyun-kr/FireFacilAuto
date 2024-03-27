package com.FireFacilAuto.service.lawService.buildinglaws;

import com.FireFacilAuto.domain.entity.building.Building;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValue;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.IntegerClauseValueWrapper;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.service.lawService.evaluators.BuildingLawEvaluator;
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
    private final BuildingLawEvaluator buildingLawEvaluator;

    @Autowired
    public BuildingLawExecutionService(BuildingLawRepositoryService lawService, ClauseFactory clauseFactory, BuildingLawEvaluator buildingLawEvaluator) {
        this.lawService = lawService;
        this.buildingLawEvaluator = buildingLawEvaluator;
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
            List<FloorResults> results = buildingLawEvaluator.evaluateLaw(blf, floorResultsList, building);
        });

        log.info("building laws applied");
    }



}
