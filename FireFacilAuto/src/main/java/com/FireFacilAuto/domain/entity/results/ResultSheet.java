package com.FireFacilAuto.domain.entity.results;

import com.FireFacilAuto.domain.entity.building.Building;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * The {@code ResultSheet} class represents a result sheet associated with a building,
 * containing a list of floor results.
 *
 * @author kimseunghyun-kr
 * @version 1.0
 */
@Data
@Entity
public class ResultSheet {

    /**
     * Identifier for the {@code ResultSheet} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The associated building for which the result sheet is generated.
     */
    @OneToOne
    private Building building;

    /**
     * The list of floor results associated with this result sheet.
     */
    @OneToMany
    private List<FloorResults> floorResultsList;
}
