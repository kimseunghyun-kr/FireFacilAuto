package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.Field;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparableComparisonStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.NonComparableComparisonStrategy;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.*;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;
import static com.FireFacilAuto.service.lawService.LawMappingUtils.getCondition;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.floorResultSheetBuilder;
import static com.FireFacilAuto.service.lawService.ResultSheetInitializingUtils.resultSheetInitializr;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.conditionParser;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Service
@Slf4j
public class FloorLawExecutionService {
    private final FloorLawService lawService;

    public FloorLawExecutionService(FloorLawService lawService) {
        this.lawService = lawService;
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

    protected void resolveFloorLawThenExecute(Building building, List<FloorResults> floorResultsList) {
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
        candidateFloorLaw.forEach(flf -> {
            log.info("----------------------------------------------------------------");
            floorConditionComparator(flf, new LinkedList<>(floorResultsList), building);
            log.info("floorResultList check at floorLawExecute {}", floorResultsList);
        });
    }

    private <T extends Comparable<T>,U> boolean compareField(Clause<?> clause, Floor floor) {
        Object lawValue = clause.getValue();
        String lawField = clause.getFieldname();
        Field<?> field = getFloorFieldByName(floor, lawField);
        Class<?> clazz = field.valueType();

        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                lawField, clazz.getSimpleName(), lawValue, lawValue.getClass().getSimpleName());

        if (lawValue instanceof String && clazz.equals(String.class)) {
            return lawValue.equals(field.value());
        }
        if (Comparable.class.isAssignableFrom(clazz) && lawValue.getClass().equals(clazz)) {
            ComparableComparisonStrategy<T> strategy = new ComparableComparisonStrategy<>();

            // Compare using the strategy
            return strategy.compare((T) field.value(), (T) lawValue, clause.getComparisonOperator());
        }
        else {
            NonComparableComparisonStrategy<U> strategy = new NonComparableComparisonStrategy<>();
            return strategy.compare((U)field.value(), (U)lawValue, clause.getComparisonOperator());
        }
    }

    private void floorConditionComparator(FloorLawFields flf, List<FloorResults> floorResultsList, Building building) {
        if (building == null || floorResultsList == null || floorResultsList.isEmpty()) {
            return;
        }

        Integer[] target = {flf.majorCategoryCode, flf.minorCategoryCode};

        log.info("surviving list beginning {}",floorResultsList );
        for(Clause<?> clause : flf.getClauses()) {
            if(clause.getFieldname().equals("floorNo")) {

            }
        }
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

        log.info("surviving list past floorNo {}",floorResultsList );
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

        log.info("surviving list past floorAreaSum {}",floorResultsList );
//      Surviving inputs are all satisfying the conditonal requirement of floorNo
//      floorAreaThreshold
        if (isActivated(flf.floorAreaThreshold)) {
            Conditions conditional = getCondition(conditions, "floorAreaThreshold");
            floorResultsList.removeIf(survivingResults -> !conditionParser(conditional.getOperator(), flf.floorAreaThreshold, survivingResults.getFloor().getFloorArea()));
        }
        if (floorResultsList.isEmpty()) {
            return;
        }

        log.info("surviving list past floorAreaThreshold {}",floorResultsList );
//      surviving inputs has floor area sum of given classification, specifiation meeting condition
//      floorMaterial
        if (isActivated(flf.floorMaterial)) {
            floorResultsList.removeIf(survivingResults -> !Objects.equals(survivingResults.getFloor().getFloorMaterial(), flf.floorMaterial));
            if (floorResultsList.isEmpty()) {
                return;
            }
        }

        log.info("surviving list past floorWindowAvailability {}",floorResultsList );
//        surviving inputs have floor materials as designated by flf
//        floorWindowAvailability
        if(isActivated(flf.floorWindowAvailability)) {
            floorResultsList.removeIf(survivingResults -> survivingResults.getFloor().getFloorWindowAvailability() != flf.getFloorWindowAvailability());
        }


        floorResultListMajorCodeMapper(floorResultsList, target);

    }

    private double calculateFloorAreaSum(List<FloorResults> floorResultsList, FloorLawFields flf) {
        double floorAreaSum = 0.0;
        Iterator<FloorResults> iterator = floorResultsList.iterator();
        while(iterator.hasNext()) {
            FloorResults survivingResults = iterator.next();
            if (flf.floorClassification == -1 ||
                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification == -1) ||
                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification.equals(survivingResults.getFloor().getFloorSpecification()))) {
                floorAreaSum += survivingResults.getFloor().floorArea;
            } else {
                iterator.remove();
            }
        }
//        for (FloorResults survivingResults : floorResultsList) {
//            if (flf.floorClassification == -1 ||
//                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification == -1) ||
//                    (flf.floorClassification.equals(survivingResults.getFloor().getFloorClassification()) && flf.floorSpecification.equals(survivingResults.getFloor().getFloorSpecification()))) {
//                floorAreaSum += survivingResults.getFloor().floorArea;
//            } else {
//                floorResultsList.remove(survivingResults);
//            }
//        }
        return floorAreaSum;
    }
}
