package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.buildingLawclauseConfig.PossibleBuildingClauses;
import lombok.Builder;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BuildingLawBuilder {

    private List<Clause<?>> clauses = new ArrayList<>();
    private int priority = 1; // Default priority value

    public BuildingLawBuilder() {
        // Initialize with default values if needed
    }

    public BuildingLawBuilder addTotalFloors(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.TOTAL_FLOORS, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addUndergroundFloors(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.UNDERGROUND_FLOORS, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addOvergroundFloors(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.OVERGROUND_FLOORS, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addGFA(double value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.GFA, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addBuildingMaterial(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.BUILDING_MATERIAL, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addLength(double value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.LENGTH, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addDateOfApproval(LocalDate value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.DATE_OF_APPROVAL, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addBuildingHumanCapacity(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.BUILDING_HUMAN_CAPACITY, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addExtraFacility(String value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.EXTRA_FACILITY, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addBuildingClassification(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.BUILDING_CLASSIFICATION, value, comparisonOperator);
        return this;
    }

    public BuildingLawBuilder addBuildingSpecification(int value, ComparisonOperator comparisonOperator) {
        addClause(PossibleBuildingClauses.BUILDING_SPECIFICATION, value, comparisonOperator);
        return this;
    }

    // Add methods for other PossibleBuildingClauses fields

    public BuildingLawBuilder next() {
        priority++;
        return this;
    }

    public List<Clause<?>> build() {
        return clauses;
    }

    private <T> void addClause(PossibleBuildingClauses field, T value, ComparisonOperator comparisonOperator) {
        Clause<T> clause = Clause.clauseFactory(field.name(), PossibleBuildingClauses.class, comparisonOperator, value, priority);
        clauses.add(clause);
    }
}

