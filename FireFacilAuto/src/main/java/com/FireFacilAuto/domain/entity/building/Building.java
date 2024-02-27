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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;

    @Embedded
    private Address juso;

    @OneToMany(mappedBy = "building")
    private List<Floor> compositeFloors;

    @ElementCollection
    @CollectionTable(name = "building_field_list", joinColumns = @JoinColumn(name = "building_UUID"))
    private List<Field<?>> buildingFieldList;

    // Additional fields as needed
}

