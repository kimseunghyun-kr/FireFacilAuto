package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.floors.Floor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Represents a building entity in the system.
 */
@Entity
@Data
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;

    @Embedded
    private Address juso;

    /**
     * composite floors present within the building
     */
    @OneToMany(mappedBy = "building")
    private List<Floor> compositeFloors;

    /**
     * fields that are to be included to describe the building
     */
    @ElementCollection
    @CollectionTable(name = "building_field_list", joinColumns = @JoinColumn(name = "building_UUID"))
    private List<Field<?>> buildingFieldList;

    // Additional fields as needed
}

