package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FloorLawFieldsRepository extends JpaRepository<FloorLawFields, Long> {
    @Query("SELECT flf FROM FloorLawFields flf " +
            "WHERE (flf.floorClassification = :classification AND (flf.floorSpecification = :specification OR flf.floorSpecification = -1))" +
            "OR (flf.floorClassification = -1)"
    )
    List<FloorLawFields> findMatchingPurpose(@Param("classification")Integer floorClassification, @Param("specification")Integer floorSpecification);
}
