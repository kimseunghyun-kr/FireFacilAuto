package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.comparisonStrategy.ComparisonStrategy;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

@Embeddable
@Data
@Slf4j
public class Clause<T>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String fieldname;
    ComparisonOperator comparisonOperator;
    T value;
    int priority;

    public Clause (){
    }

    public Clause(String fieldname, ComparisonOperator co, T input, int priority) {
        this.fieldname = fieldname;
        this.comparisonOperator = co;
        this.value=input;
        this.priority=priority;
    }

    public static <T> Clause<T> clauseFactory(String fieldname, T input, ComparisonOperator co, int priority) {
        return new Clause<>(fieldname,co,input,priority);
    }


}
