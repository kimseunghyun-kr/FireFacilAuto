package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator.defaultComparisonStrategyApply;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Slf4j
public class EvalutateOnSingleFieldStrategy implements EvaluationStrategy{

    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        if(fields.length != 1) {
            throw new UnsupportedOperationException("when calling EvaluateOnSingleFieldStrategy please only use 1 field" + Arrays.toString(fields));
        }
        String targetField = clause.getClauseField().getTargetFieldName();
        Field field = fields[0];
        assert(field.getFieldName().equals(targetField));

        if(!isActivated(field.getValue())){
            log.warn("null value detected when not supposed to. check if environment is test or not." +
                    "if this is in production setting then something critical went wrong" +
                    "error at field : {}, clause {}, fields are intended to discard if null value present",field , clause);
            return true;
        }
        String lawField = clause.getClauseField().getLawFieldName();
        Object lawValue = clause.getValue();
        Class<?> clazz = field.getValueType();
        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                field.getFieldName(), clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());
        return defaultComparisonStrategyApply(clause, lawValue, clazz, field);

    }
}
