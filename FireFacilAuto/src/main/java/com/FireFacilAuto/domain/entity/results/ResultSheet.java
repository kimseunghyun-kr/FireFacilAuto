package com.FireFacilAuto.domain.entity.results;

import com.FireFacilAuto.domain.entity.building.Building;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ResultSheet {

    @Id
    private Long id;

    @OneToOne
    private Building building;

    @OneToMany
    private List<FloorResults> floorResultsList;

}
