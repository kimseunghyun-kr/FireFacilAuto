package com.FireFacilAuto.domain;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.query.sqm.ComparisonOperator;

@Data
@Entity
public class Conditions {
    @Id
    private Long id;

    private String fieldName;
    private ComparisonOperator operator;

    @ManyToOne
    private BuildingLawFields buildingLawFields;

    @ManyToOne
    private FloorLawFields floorLawFields;


}
