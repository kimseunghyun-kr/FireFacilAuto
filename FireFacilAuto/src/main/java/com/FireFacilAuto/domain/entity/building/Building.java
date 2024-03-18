package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.domain.entity.building.field.Field;
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
public class Building implements TargetEntity {

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "field_key")
    @MapKeyClass(String.class)  // Assuming field keys are of type String
    private Map<String, Field> buildingFieldMap;

    // Additional fields as needed
}

