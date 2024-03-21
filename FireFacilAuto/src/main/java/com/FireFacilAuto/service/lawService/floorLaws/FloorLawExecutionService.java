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
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.*;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.floorResultSheetBuilder;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;

@Service
@Slf4j
public class FloorLawExecutionService {
    private final FloorLawRepositoryService lawService;
    private final ClauseFactory clauseFactory;

    public FloorLawExecutionService(FloorLawRepositoryService lawService, ClauseFactory clauseFactory) {
        this.lawService = lawService;
        this.clauseFactory = clauseFactory;
    }

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

        log.info("receiving all appicable candidate for floor laws," +
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


    private void floorLawExecute(Building building, List<FloorResults> floorResultsList, List<FloorLawFields> candidateFloorLaw) {
        List<Floor> floors = building.getCompositeFloorsList();
        candidateFloorLaw.forEach(flf -> {
            log.info("----------------------------------------------------------------");
            floorConditionComparator(flf, new LinkedList<>(floorResultsList), building);
            log.info("floorResultList check at floorLawExecute {}", floorResultsList);
        });
    }

    private void floorConditionComparator(FloorLawFields flf, List<FloorResults> floorResultsList, Building building) {
        if (building == null || floorResultsList == null || floorResultsList.isEmpty()) {
            return;
        }

        Integer[] target = {flf.majorCategoryCode, flf.minorCategoryCode};

        log.info("surviving list beginning {}",floorResultsList);
        int greatestEpoch = 1;

        if(flf.floorClassification != -1) {
            IntegerClauseValueWrapper classificationClauseValue = new IntegerClauseValueWrapper(flf.floorClassification, ClauseValue.INTEGER);
            Clause classificationClause = clauseFactory.createClauseWithClauseValueWrapper("floorClassification", ClauseTypes.FloorClauses, ComparisonOperator.EQUAL, classificationClauseValue, 1);
            if (flf.floorSpecification != -1) {
                IntegerClauseValueWrapper specificationClauseValue = new IntegerClauseValueWrapper(flf.floorSpecification, ClauseValue.INTEGER);
                Clause specificationClause = clauseFactory.createClauseWithClauseValueWrapper("floorSpecification", ClauseTypes.FloorClauses, ComparisonOperator.EQUAL, specificationClauseValue, 1);
                flf.clauses.addFirst(specificationClause);
            }
            flf.clauses.addFirst(classificationClause);
        }

        List<FloorResults> nextEpochSurvivor = new LinkedList<>(); // Create a list to store elements that survives

        for(Clause clause : flf.getClauses()) {
            if(clause.getPriority() > greatestEpoch) {
                floorResultsList = nextEpochSurvivor;
                nextEpochSurvivor = new LinkedList<>();
                greatestEpoch = clause.getPriority();
            }
            if(floorResultsList.isEmpty()) {
                return;
            }

            nextEpochSurvivor.addAll(ClauseEvaluator.evaluateFloor(clause,building,floorResultsList));
        }

        floorResultsList = nextEpochSurvivor;

        floorResultListMajorCodeMapper(floorResultsList, target);
    }

}
