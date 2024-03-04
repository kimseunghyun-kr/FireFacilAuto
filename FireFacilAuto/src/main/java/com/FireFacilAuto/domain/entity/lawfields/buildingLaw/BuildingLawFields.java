package com.FireFacilAuto.domain.entity.lawfields.buildingLaw;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;


/**
 * Represents the fields related to building laws and regulations.
 */
@Data
@Entity
public class BuildingLawFields {

    /**
     * Internal system identification code for the building law fields.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_law_field_id")
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
     * Building classification code indicating the primary use of the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingClassification;

    /**
     * Building specification code indicating the detailed use of the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingSpecification;

    @ElementCollection
    @CollectionTable(name = "building_clauses", joinColumns = @JoinColumn(name = "building_law_field_id"))
    public List<Clause<?>> clauses;

}

