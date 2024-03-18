package com.FireFacilAuto.domain.entity.lawfields.floorLaw;

import com.FireFacilAuto.domain.entity.lawfields.LawFields;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the legal conditions applicable to individual floors within a building.
 * Each field represents a condition, and these conditions have an AND relationship.
 * For example, if floorClassification = 1 AND floorNo > 3 AND floorWindowAvailability = false,
 * the law applies to floors with classification 1, higher than the 3rd floor, and without windows.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@DiscriminatorValue("FloorLawFields")
@Entity
public class FloorLawFields extends LawFields {

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


}

