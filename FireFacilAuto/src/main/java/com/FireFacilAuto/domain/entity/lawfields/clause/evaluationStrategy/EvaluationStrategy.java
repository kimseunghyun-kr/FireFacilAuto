package com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.field.Field;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.results.FloorResults;

import java.util.List;

public interface EvaluationStrategy {
    Boolean evaluate(Clause clause, Field... fields);
}
