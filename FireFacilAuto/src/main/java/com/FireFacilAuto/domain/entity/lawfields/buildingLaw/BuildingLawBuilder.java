package com.FireFacilAuto.domain.entity.lawfields.buildingLaw;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseFactory;
import com.FireFacilAuto.domain.entity.lawfields.clause.ClauseTypes;
import lombok.Builder;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class BuildingLawBuilder {

    private List<Clause<?>> clauses = new ArrayList<>();
    private final ClauseFactory clauseFactory;
    private int priority = 1; // Default priority value

    @Autowired
    public BuildingLawBuilder(ClauseFactory clauseFactory) {
        // Initialize with default values if needed
        this.clauseFactory = clauseFactory;
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
        Clause<T> clause = clauseFactory.createClause(field.name(), ClauseTypes.PossibleBuildingClauses, comparisonOperator, value, priority);
        clauses.add(clause);
    }
}

