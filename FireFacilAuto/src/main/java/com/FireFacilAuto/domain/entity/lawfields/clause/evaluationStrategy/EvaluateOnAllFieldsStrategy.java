package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.BuildingUtils;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.results.FloorResults;

import java.util.List;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

public class EvaluateOnAllFieldsStrategy implements EvaluationStrategy {

    @Override
    public Boolean evaluate(Clause clause, Field... fields) {
        return null;
    }
}
