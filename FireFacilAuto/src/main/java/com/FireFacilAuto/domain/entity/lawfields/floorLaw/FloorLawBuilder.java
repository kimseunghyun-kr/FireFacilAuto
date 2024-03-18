package com.FireFacilAuto.domain.entity.lawfields.floorLaw;


import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorLawCauses;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class FloorLawBuilder {

    private List<Clause> clauses = new ArrayList<>();
    private FloorLawFields floorLawFields = new FloorLawFields();
    private final ClauseFactory clauseFactory = new ClauseFactory();
    private int priority = 1; // Default priority value

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

    public FloorLawBuilder setTargetFloor (Integer buildingClassification, Integer buildingSpecification) {
        floorLawFields.setFloorClassification(buildingClassification);
        floorLawFields.setFloorSpecification(buildingSpecification);
        return this;
    }

    public FloorLawBuilder setTargetlaw (Integer majorityCode, Integer minorityCode) {
        floorLawFields.setMajorCategoryCode(majorityCode);
        floorLawFields.setMinorCategoryCode(minorityCode);
        return this;
    }

    // Add methods for other PossibleFloorLawCauses fields

    public FloorLawBuilder next() {
        priority++;
        return this;
    }

    public FloorLawFields buildThenReset() {
        floorLawFields.setClauses(this.clauses);
        FloorLawFields result = this.floorLawFields;
        reset();
        return result;
    }

    public void reset() {
        this.priority = 1;
        this.floorLawFields = new FloorLawFields();
        this.clauses = new LinkedList<>();
    }

    public List<Clause> buildListNoReset() {
        return clauses;
    }

    private <T> void addClause(PossibleFloorLawCauses field, T value, ComparisonOperator comparisonOperator) {
        Clause clause = clauseFactory.createClause(field.name(), ClauseTypes.PossibleFloorClauses, comparisonOperator, value, priority);
        clauses.add(clause);
    }

}
