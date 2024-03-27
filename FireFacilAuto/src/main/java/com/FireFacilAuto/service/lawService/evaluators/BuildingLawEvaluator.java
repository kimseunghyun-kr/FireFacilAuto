package com.FireFacilAuto.service.lawService.evaluators;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvalutateOnSingleFieldStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.SentinelEvaluationStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValue;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.IntegerClauseValueWrapper;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.FireFacilAuto.service.lawService.LawMappingUtils.floorResultListMajorCodeMapper;
@Slf4j
@Service
public class BuildingLawEvaluator implements LawEvaluator<BuildingLawFields, Building> {
    private final ClauseFactory clauseFactory;

    @Autowired
    public BuildingLawEvaluator(ClauseFactory clauseFactory) {
        this.clauseFactory = clauseFactory;
    }


    public Boolean evaluateSingleBuilding(Clause clause, Building building) {
        String targetField = clause.getClauseField().getTargetFieldName();
        EvaluationType evaluationType = clause.getEvaluationType();
        EvaluationStrategy strategyToUse = new SentinelEvaluationStrategy();
        Field field = BuildingUtils.getBuildingFieldByName(building, targetField);

        if (Objects.requireNonNull(evaluationType) == EvaluationType.SINGLE) {
            strategyToUse = new EvalutateOnSingleFieldStrategy();
        }

        // left this way as in the future Building may have aggregate? fields or other methods of evaluation. as of current,
        // only valid way is singular

        return strategyToUse.evaluate(clause, field);
    }

    @Override
    public List<FloorResults> applicableMethodResolver(BuildingLawFields lawFields, List<FloorResults> resultsList, Building target) {
        Integer[] lawTarget = {lawFields.majorCategoryCode, lawFields.minorCategoryCode};
        switch(lawFields.applicationMethod) {
            case ALL -> floorResultListMajorCodeMapper(resultsList, lawTarget);
            case APPLICABLEONLY -> throw new UnsupportedOperationException("ApplicableAll mode of operation is unsupported for Buildings");
            case SINGLE -> throw new UnsupportedOperationException("Single mode of application is unsupported for Building");
        }
        return resultsList;
    }

    @Override
    public List<FloorResults> evaluateLaw(BuildingLawFields lawFields, List<FloorResults> resultsList, Building target) {
        if(lawFields.buildingClassification != -1) {
            IntegerClauseValueWrapper classificationClauseValue = new IntegerClauseValueWrapper(lawFields.buildingClassification, ClauseValue.INTEGER);
            Clause classificationClause = clauseFactory.createClauseWithClauseValueWrapper("buildingClassification", ClauseTypes.BuildingClauses, ComparisonOperator.EQUAL, classificationClauseValue, 1);
            if (lawFields.buildingSpecification != -1) {
                IntegerClauseValueWrapper specificationClauseValue = new IntegerClauseValueWrapper(lawFields.buildingSpecification, ClauseValue.INTEGER);
                Clause specificationClause = clauseFactory.createClauseWithClauseValueWrapper("buildingSpecification", ClauseTypes.BuildingClauses, ComparisonOperator.EQUAL, specificationClauseValue, 1);
                lawFields.clauses.addFirst(specificationClause);
            }
            lawFields.clauses.addFirst(classificationClause);
        }


//        FOR A SINGLE(THE) BLF, IF ALL MATCH THEN LABEL ALL FLOORS TRUE
        boolean isTrue = true;
        for (Clause clause : lawFields.clauses) {
            isTrue &= this.evaluateSingleBuilding(clause, target);
        }
        log.info("BLF PASSING ?= {} ", isTrue);
        if (isTrue) {
            return this.applicableMethodResolver(lawFields, resultsList, target);
        }
        return EMPTYSENTINELLIST;
    }
}
