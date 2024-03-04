package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.buildingLawclauseConfig.PossibleBuildingClauses;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.floorLawClauseConfig.PossibleFloorLawCauses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class PossibleLawFieldRegistrar {

    private final PossibleLawFieldRegistry registry;

    @Autowired
    public PossibleLawFieldRegistrar(PossibleLawFieldRegistry registry) {
        this.registry = registry;
        // Register your enums with the registry
        registry.register(PossibleFloorLawCauses.class);
        registry.register(PossibleBuildingClauses.class);
        // Add more as needed
    }
}