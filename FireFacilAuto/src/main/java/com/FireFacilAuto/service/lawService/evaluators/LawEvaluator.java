package com.FireFacilAuto.service.lawService.evaluators;

import com.FireFacilAuto.domain.entity.building.TargetEntity;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.LawFields;
import com.FireFacilAuto.domain.entity.results.FloorResults;
import com.FireFacilAuto.domain.entity.results.ResultSheet;

import java.util.LinkedList;
import java.util.List;

public interface LawEvaluator<T extends LawFields, U extends TargetEntity> {
    public static final List<FloorResults> EMPTYSENTINELLIST = new LinkedList<>();
    public List<FloorResults> applicableMethodResolver(T lawFields, List<FloorResults> resultsList, U target);
    public List<FloorResults> evaluateLaw(T lawFields, List<FloorResults> resultsList, U target);

}
