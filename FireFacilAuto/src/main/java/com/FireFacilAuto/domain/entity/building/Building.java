package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.Address;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a building entity in the system.
 */
@Entity
@Data
public class Building {

    /**
     * Internal system identification code for the building.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Address information for the building.
     */
    @Embedded
    public Address juso;

    /**
     * List of floors within the building.
     */
    @OneToMany(mappedBy = "building")
    public List<Floor> compositeFloors;

    /**
     * Total number of floors in the building.
     */
    public Integer totalFloors;

    /**
     * Number of underground floors in the building.
     */
    public Integer undergroundFloors;

    /**
     * Number of overground floors in the building.
     */
    public Integer overgroundFloors;

    /**
     * Gross floor area of the building.
     */
    public Double GFA;

    /**
     * Classification code indicating the primary use of the building.
     */
    public Integer buildingClassification;

    /**
     * Specific code indicating the detailed use of the building.
     */
    public Integer buildingSpecification;

    /**
     * Code indicating the primary structural material of the building.
     */
    public Integer BuildingMaterial;

    /**
     * Distance from the building to underground structures like tunnels.
     */
    public Double length;

    /**
     * Date when the building received approval for use.
     */
    public LocalDate dateofApproval;

    // Unspecified information (you can update these comments as needed)

    /**
     * Capacity of humans within the building (not yet included).
     */
    public Integer buildingHumanCapacity;

    // Additional information or context if needed
}
