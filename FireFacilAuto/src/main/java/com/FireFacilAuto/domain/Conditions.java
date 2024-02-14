package com.FireFacilAuto.domain;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

@Data
@Entity
public class Conditions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fieldName;

    private ComparisonOperator operator;

    @ManyToOne
    @JoinColumn(name = "building_law_fields_id")
    private BuildingLawFields buildingLawFields;

    @ManyToOne
    @JoinColumn(name = "floor_law_fields_id")
    private FloorLawFields floorLawFields;


}
