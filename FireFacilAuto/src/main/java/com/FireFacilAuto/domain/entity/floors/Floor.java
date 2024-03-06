package com.FireFacilAuto.domain.entity.floors;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.field.Field;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "field_key")
    @MapKeyClass(String.class)
    private Map<String, Field> floorFieldMap;
}

