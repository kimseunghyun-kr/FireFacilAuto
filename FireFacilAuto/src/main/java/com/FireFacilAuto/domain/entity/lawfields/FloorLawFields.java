package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

/**
 * Represents the legal conditions applicable to individual floors within a building.
 * Each field represents a condition, and these conditions have an AND relationship.
 * For example, if floorClassification = 1 AND floorNo > 3 AND floorWindowAvailability = false,
 * the law applies to floors with classification 1, higher than the 3rd floor, and without windows.
 */
@Data
@Entity
public class FloorLawFields {

    /**
     * Internal system identification code for the floor law fields.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Major category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer majorCategoryCode;

    /**
     * Minor category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer minorCategoryCode;

    /**
     * Classification code for the primary purpose of the floor.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer floorClassification;

    /**
     * Specification code for additional details about the floor.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer floorSpecification;

    @ElementCollection
    @CollectionTable(name = "floor_clauses", joinColumns = @JoinColumn(name = "floor_id"))
    public List<Clause<?>> clauses;

}

