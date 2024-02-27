package com.FireFacilAuto.domain.entity.floors;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.building.Field;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "floor_field_list", joinColumns = @JoinColumn(name = "floor_UUID"))
    public List<Field<?>> floorFieldList;
}

