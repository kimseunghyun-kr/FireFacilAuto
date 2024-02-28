package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig.PossibleBuildingClauses;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;


@Embeddable
@Data
@Slf4j
public class Clause<T>{

    String fieldname;
    ComparisonOperator comparisonOperator;
    T value;
    int priority;
    Class<?> token;

    public Clause (){
    }

    public Clause(String fieldname, ComparisonOperator co, T input, int priority, Class<?> token) {
        this.fieldname = fieldname;
        this.comparisonOperator = co;
        this.value=input;
        this.priority=priority;
        this.token = token;
    }

    public static <T> Clause<T> clauseFactory(String fieldname, T input, ComparisonOperator co, int priority) {
        return new Clause<>(fieldname,co,input,priority, PossibleBuildingClauses.getBuildingLawClassToken(fieldname));
    }


}
