package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.FireFacilAuto.domain.entity.lawfields.clause.evaluationStrategy.EvaluationType;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

//@JsonDeserialize(using = ClauseDeserializer.class)
@Entity
@Data
@Slf4j
public class Clause{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    public PossibleClauses clauseField;
    public ClauseTypes clauseTypes;
    public ComparisonOperator comparisonOperator;
    @OneToOne(cascade = CascadeType.ALL)
    public ClauseValueWrapper valueWrapper;
    public int priority;
    public EvaluationType evaluationType;


    public Clause (){
    }

    protected Clause (PossibleClauses clauseField,
                      ClauseTypes clauseTypes,
                      ComparisonOperator co,
                      ClauseValueWrapper valueWrapper,
                      int priority,
                      EvaluationType evaluationType) {
        this.clauseField = clauseField;
        this.clauseTypes = clauseTypes;
        this.comparisonOperator = co;
        this.valueWrapper=valueWrapper;
        this.priority=priority;
        this.evaluationType = evaluationType;
    }

    // Get the valueType from the stored class name
    public Class<?> getToken() {
        return clauseField.getFieldType().getCorrespondingClass();
    }

    public Object getValue() {
        return valueWrapper.getValue();
    }

}
