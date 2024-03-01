package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.floors.Floor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private List<Floor> compositeFloorsList;

    /**
     * fields that are to be included to describe the building
     */
    @ElementCollection
    @CollectionTable(name = "building_field_map", joinColumns = @JoinColumn(name = "building_UUID"))
    @MapKeyColumn(name = "field_key")
    private Map<String, Field<?>> buildingFieldMap;

    // Additional fields as needed
}

