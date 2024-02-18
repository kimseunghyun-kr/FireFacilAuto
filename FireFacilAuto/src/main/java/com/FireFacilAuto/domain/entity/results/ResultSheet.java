package com.FireFacilAuto.domain.entity.results;

import com.FireFacilAuto.domain.entity.building.Building;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ResultSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Building building;

    @OneToMany
    private List<FloorResults> floorResultsList;

}
