package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuildingLawFieldsRepository extends JpaRepository<BuildingLawFields, Long> {
    @Query("SELECT blf FROM BuildingLawFields blf " +
            "WHERE (blf.buildingClassification = :classification AND (blf.buildingSpecification = :specification OR blf.buildingSpecification = -1))" +
            "OR (blf.buildingClassification = -1)"
    )
    List<BuildingLawFields> findMatchingPurpose(@Param("classification") Integer classification,
                                                        @Param("specification") Integer specification);
}
