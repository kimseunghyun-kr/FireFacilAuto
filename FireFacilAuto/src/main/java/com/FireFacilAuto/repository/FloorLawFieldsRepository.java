package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.entity.lawfields.floorLaw.FloorLawFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The {@code FloorLawFieldsRepository} interface provides methods to interact with the database
 * for querying and managing {@link FloorLawFields} entities using Spring Data JPA.
 * <p>
 * It extends {@link JpaRepository} for CRUD operations on the {@link FloorLawFields} entity.
 * </p>
 * <p>
 * This repository includes a custom query method to find floor law fields matching specified classification and specification criteria.
 * </p>
 * <p>
 * The custom query uses the {@code @Query} annotation to execute a JPQL query.
 * </p>
 *
 * @author trienebutdiene (or kimseunghyun-kr)
 * @version 1.0
 */
public interface FloorLawFieldsRepository extends JpaRepository<FloorLawFields, Long> {

    /**
     * Custom query method to find floor law fields matching specified classification and specification criteria.
     *
     * @param floorClassification The floor classification code.
     * @param floorSpecification  The floor specification code.
     * @return A list of {@link FloorLawFields} entities matching the criteria.
     */
    @Query("SELECT flf FROM FloorLawFields flf " +
            "WHERE (flf.floorClassification = :classification AND (flf.floorSpecification = :specification OR flf.floorSpecification = -1))" +
            "OR (flf.floorClassification = -1)"
    )
    List<FloorLawFields> findMatchingPurpose(@Param("classification") Integer floorClassification, @Param("specification") Integer floorSpecification);
}
