package com.FireFacilAuto.FireFacilAuto.service.lawservice.lawBuilderTest;

import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawBuilder;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Slf4j
public class BuildingLawBuilderTest {
    private final BuildingLawBuilder buildingLawBuilder;

    @Autowired
    public BuildingLawBuilderTest(BuildingLawBuilder buildingLawBuilder) {
        this.buildingLawBuilder = buildingLawBuilder;
    }

    @Test
    void buildingLawSee() {
        List<Clause<?>> blaw =buildingLawBuilder
                .addBuildingClassification(1, ComparisonOperator.EQUAL)
                .addBuildingSpecification(1, ComparisonOperator.EQUAL)
                .addBuildingMaterial(1, ComparisonOperator.EQUAL)
                .addBuildingMaterial(2, ComparisonOperator.EQUAL)
                .next()
                .addDateOfApproval(LocalDate.now(), ComparisonOperator.LESS_THAN_OR_EQUAL)
                .addGFA(33.0, ComparisonOperator.GREATER_THAN_OR_EQUAL)
                .build();

        log.info("BLAW AT {}", blaw);
    }
}
