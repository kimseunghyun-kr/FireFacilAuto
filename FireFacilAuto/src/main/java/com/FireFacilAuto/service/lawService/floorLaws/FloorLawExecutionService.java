package com.FireFacilAuto.service.lawService.floorLaws;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.clause.*;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValue;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.IntegerClauseValueWrapper;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.service.lawService.evaluators.FloorLawEvaluator;
import com.FireFacilAuto.service.lawService.evaluators.LawEvaluator;
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.*;
import static com.FireFacilAuto.domain.entity.lawfields.LawUtils.clausePrioritysort;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.floorResultSheetBuilder;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;

@Service
@Slf4j
public class FloorLawExecutionService {
    private final FloorLawRepositoryService lawService;
    private final FloorLawEvaluator floorLawEvaluator;

    public FloorLawExecutionService(FloorLawRepositoryService lawService, FloorLawEvaluator floorLawEvaluator) {
        this.lawService = lawService;
        this.floorLawEvaluator = floorLawEvaluator;
    }

    @Deprecated
    public ResultSheet executeLaw(Building building) {
        if (building == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }

        log.info("initializing result sheets");
        ResultSheet resultSheet = resultSheetInitializr(building);

        List<FloorResults> floorResultsList = floorResultSheetBuilder(building);
        log.info("executing floor laws");
        resolveFloorLawThenExecute(building, floorResultsList);

        return resultSheet;
    }

    public void resolveFloorLawThenExecute(Building building, List<FloorResults> floorResultsList) {
        Set<Pair> floorResultStore = new HashSet<>();
        List<FloorLawFields> candidateFloorLaw = new LinkedList<>();

        log.info("receiving all applicable candidate for floor laws," +
                " floors may have differing classification and specification from building");
        floorLawCandidacyResolver(floorResultsList, floorResultStore, candidateFloorLaw);

        log.info("applying floor laws");
        floorLawExecute(building, floorResultsList, candidateFloorLaw);

    }

    private void floorLawCandidacyResolver(List<FloorResults> floorResultsList, Set<Pair> floorResultStore, List<FloorLawFields> candidateFloorLaw) {
        for (FloorResults floorResults : floorResultsList) {
            Floor floor = floorResults.getFloor();
            Pair p = new Pair(getFloorClassification(floor), getFloorSpecification(floor));
            if (floorResultStore.add(p)) {
                candidateFloorLaw.addAll(lawService.getLawsWithApplicablePurpose(floor));
            }
        }
    }


    public void floorLawExecute(Building building, List<FloorResults> floorResultsList, List<FloorLawFields> candidateFloorLaw) {
        List<Floor> floors = building.getCompositeFloorsList();
        candidateFloorLaw.forEach(flf -> {
            log.info("----------------------------------------------------------------");
            clausePrioritysort(flf.clauses);
            List<FloorResults> checkedResults = floorLawEvaluator.evaluateLaw(flf, floorResultsList, building);
            log.info("checkedResults is {}", checkedResults);
            log.info("checking for mutated reference for floorResultList at floorLawExecute {}", floorResultsList);
        });
    }

}
