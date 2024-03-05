package com.FireFacilAuto.domain.entity.lawfields.floorLaw;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorLawCauses;
import lombok.Builder;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class FloorLawBuilder {

    private List<Clause<?>> clauses = new ArrayList<>();
    private final ClauseFactory clauseFactory;
    private int priority = 1; // Default priority value

    @Autowired
    public FloorLawBuilder(ClauseFactory clauseFactory) {
        // Initialize with default values if needed
        this.clauseFactory = clauseFactory;
    }

    public FloorLawBuilder addFloorClassification(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_CLASSIFICATION, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorSpecification(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_SPECIFICATION, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorNo(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_NO, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addIsUnderground(boolean value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.IS_UNDERGROUND, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorAreaSum(double value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_AREA_SUM, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorAreaThreshold(double value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_AREA_THRESHOLD, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorMaterial(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_MATERIAL, value, comparisonOperator);
        return this;
    }

    public FloorLawBuilder addFloorWindowAvailability(boolean value, ComparisonOperator comparisonOperator) {
        addClause(PossibleFloorLawCauses.FLOOR_WINDOW_AVAILABILITY, value, comparisonOperator);
        return this;
    }

    // Add methods for other PossibleFloorLawCauses fields

    public FloorLawBuilder next() {
        priority++;
        return this;
    }

    public List<Clause<?>> build() {
        return clauses;
    }

    private <T> void addClause(PossibleFloorLawCauses field, T value, ComparisonOperator comparisonOperator) {
        Clause<T> clause = clauseFactory.createClause(field.name(), ClauseTypes.PossibleFloorClauses, comparisonOperator, value, priority);
        clauses.add(clause);
    }

}
