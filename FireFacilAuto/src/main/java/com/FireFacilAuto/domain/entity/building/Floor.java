package com.FireFacilAuto.domain.entity.building;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a floor within a building in the system.
 */
@Entity
@Data
@ToString(exclude = "building")
public class Floor {

    /**
     * Internal system identification code for the floor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * The building associated with the floor.
     */
    @ManyToOne
    public Building building;

    /**
     * Floor number or level within the building.
     */
    public Integer floorNo;

    /**
     * Indicates whether the floor is underground or above ground.
     */
    public Boolean isUnderGround;

    /**
     * Code indicating the primary use of the floor.
     */
    public Integer floorClassification;

    /**
     * Specific code indicating the detailed use of the floor.
     */
    public Integer floorSpecification;

    /**
     * Area of the floor's surface.
     */
    public Double floorArea;

    /**
     * Code indicating the primary material of the floor.
     */
    public Integer floorMaterial;

    // information not yet included due to absence from address api

    /**
     * Availability of windows on the floor (not yet included).
     */
    public Boolean floorWindowAvailability;

    // Additional information or context if needed
}

