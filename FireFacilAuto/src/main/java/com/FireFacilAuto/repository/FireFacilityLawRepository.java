package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.entity.lawfields.FireFacilityLaw;
import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FireFacilityLawRepository extends JpaRepository<FireFacilityLaw, Long> {

    /**
     * Custom query method to find floor law fields matching specified classification and specification criteria.
     *
     * @param floorClassification The floor classification code.
     * @param floorSpecification  The floor specification code.
     * @return A list of {@link FloorLawFields} entities matching the criteria.
     */
    @Query("SELECT flf FROM FireFacilityLaw flf " +
            "WHERE (flf.classification = :classification AND (flf.specification = :specification OR flf.specification = -1))" +
            "OR (flf.classification = -1)"
    )
    List<FireFacilityLaw> findMatchingPurpose(@Param("classification") Integer floorClassification, @Param("specification") Integer floorSpecification);

}
