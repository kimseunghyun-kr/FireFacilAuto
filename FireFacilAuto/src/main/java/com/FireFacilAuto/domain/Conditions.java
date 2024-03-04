package com.FireFacilAuto.domain;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.query.sqm.ComparisonOperator;

/**
 * The {@code Conditions} class represents a condition entity associated with building or floor law fields.
 * It defines a condition with a specific field name, comparison operator, and association with either
 * building law fields or floor law fields.
 *
 * @author kimseunghyun-kr
 * @version 1.0
 */
@Data
@Entity
@ToString(exclude = {"buildingLawFields", "floorLawFields"})
public class Conditions {

    /**
     * Identifier for the {@code Conditions} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the field for which the condition is defined.
     */
    private String fieldName;

    /**
     * The comparison operator used in the condition.
     */
    private ComparisonOperator operator;

    /**
     * The associated building law fields for the condition.
     */
    @ManyToOne
    @JoinColumn(name = "building_law_fields")
    private BuildingLawFields buildingLawFields;

    /**
     * The associated floor law fields for the condition.
     */
    @ManyToOne
    @JoinColumn(name = "floor_law_fields_id")
    private FloorLawFields floorLawFields;

    // Omitted getters and setters, generated by Lombok

}
