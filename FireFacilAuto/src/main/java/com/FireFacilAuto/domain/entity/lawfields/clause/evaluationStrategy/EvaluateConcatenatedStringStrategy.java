package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.building.field.StringField;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparisonStrategy;
import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.StringComparisonStrategy;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

import java.util.Arrays;

import static com.FireFacilAuto.domain.entity.lawfields.clause.ClauseEvaluator.defaultComparisonStrategyApply;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

//they have the concatenated String of the given field.
// ComparisonOperator.equals
//they have field -> they do not have the field stipulated by the clause -> false
//they have field -> they have the field stipulated by the clause -> true
//ComparisonOperator.notequals
//they have field -> they do not have the field stipulated by the clause -> true
//they have field -> they have the field stipulated by the clause -> false
@Slf4j
public class EvaluateConcatenatedStringStrategy implements EvaluationStrategy{
    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        if (fields.length != 1) {
            throw new UnsupportedOperationException("when calling EvaluateOnSingleFieldStrategy please only use 1 field" + Arrays.toString(fields));
        }
        String targetField = clause.getClauseField().getTargetFieldName();

        StringField field;
        try {
            field = (StringField) fields[0];
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("the field provided in the search was not a String value containing field . please recheck. ", e);
        }
        assert (field.getFieldName().equals(targetField));

        if (!isActivated(field.getValue())) {
            log.warn("null value detected when not supposed to. check if environment is test or not." +
                    "if this is in production setting then something critical went wrong" +
                    "error at field : {}, clause {}, fields are intended to discard if null value present", field, clause);
            return false;
        }


        String lawValue = (String) clause.getValue();
        Class<?> clazz = field.getValueType();
        log.info("Comparing field '{}' of type '{}' with lawValue '{}' of type '{}'",
                field.getFieldName(), clazz.getSimpleName(), clause.getValue(), clause.getValue().getClass().getSimpleName());

        String[] facilitiesPresent = field.getValue().split("/");

        boolean isContains = false;

        ComparisonOperator lawFieldComparisonOperator = clause.getComparisonOperator();
        for (String facilityString : facilitiesPresent) {
            boolean meetsCondition = switch (lawFieldComparisonOperator) {
                case EQUAL -> facilityString.equals(lawValue);
                case NOT_EQUAL -> !facilityString.equals(lawValue);
                default ->
                        throw new UnsupportedOperationException("Only equals and not equals are allowed for concatenated Strings");
            };

            // Determine if the current facilityString meets the condition based on the ComparisonOperator

            // If the condition is met, set isContains to true and break the loop
            if (meetsCondition) {
                isContains = true;
                break;
            }
        }
        return isContains;
    }
}
