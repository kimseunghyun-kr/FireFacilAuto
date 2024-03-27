package com.FireFacilAuto.service.lawService.evaluators;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.floors.FloorUtils;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluateByAggregateFieldStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvalutateOnSingleFieldStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValue;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.IntegerClauseValueWrapper;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;

@Slf4j
@Service
public class FloorLawEvaluator implements LawEvaluator<FloorLawFields, Building> {
    private final ClauseFactory clauseFactory;
    private List<FloorResults> originalFloorResultList;

    @Autowired
    public FloorLawEvaluator(ClauseFactory clauseFactory) {
        this.clauseFactory = clauseFactory;
    }

    private <T extends Number & Comparable<T>> List<FloorResults> evaluateFloor(Clause clause, Building building, List<FloorResults> survivingFloors) {
        EvaluationType evaluationType = clause.getEvaluationType();
        String targetField = clause.getClauseField().getTargetFieldName();
        if(evaluationType.equals(EvaluationType.SINGLE)) {
            List<FloorResults> nextEpochSurvivor = new LinkedList<>(survivingFloors.stream()
                    .filter(survivingFloor -> {
                        Field field = FloorUtils.getFloorFieldByName(survivingFloor.getFloor(), targetField);
                        return new EvalutateOnSingleFieldStrategy().evaluate(clause, field);
                    })
                    .toList());
            return nextEpochSurvivor;
        }

        if(evaluationType.equals(EvaluationType.AGGREGATE_ALL)) {
            Field[] field = building.getCompositeFloorsList().stream()
                    .map(buildingFloor -> FloorUtils.getFloorFieldByName(buildingFloor, targetField))
                    .toArray(Field[]::new);
            if(new EvaluateByAggregateFieldStrategy<T>().evaluate(clause,field)) {
                return survivingFloors;
            } else {
                return EMPTYSENTINELLIST;
            }
        }

        if(evaluationType.equals(EvaluationType.AGGREGATE_REMAINING)) {
            Field[] field = survivingFloors.stream()
                    .map(survivingFloor -> FloorUtils.getFloorFieldByName(survivingFloor.getFloor(), targetField))
                    .toArray(Field[]::new);
            if(new EvaluateByAggregateFieldStrategy<T>().evaluate(clause,field)) {
                return survivingFloors;
            } else {
                return EMPTYSENTINELLIST;
            }
        }

        throw new UnsupportedOperationException("this is an unsupported evaluation option for Floors. please re-evaluate what is going on");
    }

    @Override
    public List<FloorResults> applicableMethodResolver(FloorLawFields lawFields, List<FloorResults> resultsList, Building target) {
        Integer[] lawTarget = {lawFields.majorCategoryCode, lawFields.minorCategoryCode};
        switch(lawFields.applicationMethod) {
            case ALL -> {
                floorResultListMajorCodeMapper(originalFloorResultList, lawTarget);
                return originalFloorResultList;
            }
            case APPLICABLEONLY -> {
                floorResultListMajorCodeMapper(resultsList, lawTarget);
                return resultsList;
            }
            case SINGLE -> throw new UnsupportedOperationException("Single mode of application is unsupported for Floors");
        }
        throw new UnsupportedOperationException("unknown mode of application is unsupported for Floors");
    }

    @Override
    public List<FloorResults> evaluateLaw(FloorLawFields lawFields, List<FloorResults> resultsList, Building target) {
        this.originalFloorResultList = resultsList;
        List<FloorResults> mutableResultListCopy = new LinkedList<>(resultsList);
        log.info("surviving list beginning {}",resultsList);
        int greatestEpoch = 1;

        if(lawFields.floorClassification != -1) {
            IntegerClauseValueWrapper classificationClauseValue = new IntegerClauseValueWrapper(lawFields.floorClassification, ClauseValue.INTEGER);
            Clause classificationClause = clauseFactory.createClauseWithClauseValueWrapper("floorClassification", ClauseTypes.FloorClauses, ComparisonOperator.EQUAL, classificationClauseValue, 1);
            if (lawFields.floorSpecification != -1) {
                IntegerClauseValueWrapper specificationClauseValue = new IntegerClauseValueWrapper(lawFields.floorSpecification, ClauseValue.INTEGER);
                Clause specificationClause = clauseFactory.createClauseWithClauseValueWrapper("floorSpecification", ClauseTypes.FloorClauses, ComparisonOperator.EQUAL, specificationClauseValue, 1);
                lawFields.clauses.addFirst(specificationClause);
            }
            lawFields.clauses.addFirst(classificationClause);
        }

        List<FloorResults> nextEpochSurvivor = new LinkedList<>(); // Create a list to store elements that survives

        for(Clause clause : lawFields.getClauses()) {
            if(clause.getPriority() > greatestEpoch) {
                mutableResultListCopy = nextEpochSurvivor;
                nextEpochSurvivor = new LinkedList<>();
                greatestEpoch = clause.getPriority();
            }
            if(mutableResultListCopy.isEmpty()) {
                return EMPTYSENTINELLIST;
            }

            nextEpochSurvivor.addAll(this.evaluateFloor(clause,target,mutableResultListCopy));
        }

        mutableResultListCopy = nextEpochSurvivor;

        return this.applicableMethodResolver(lawFields, mutableResultListCopy, target);
    }
}
