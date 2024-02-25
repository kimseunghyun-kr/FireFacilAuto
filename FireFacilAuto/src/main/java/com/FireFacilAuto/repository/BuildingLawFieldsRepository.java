package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The {@code BuildingLawFieldsRepository} interface provides methods to interact with the database
 * for querying and managing {@link BuildingLawFields} entities using Spring Data JPA.
 * <p>
 * It includes a custom query method to find building law fields matching specified classification and specification criteria.
 * </p>
 * <p>
 * This repository is a Spring Data JPA repository, and implementation details are handled by CGLIB proxies.
 * </p>
 *
 * @author trienebutdiene (or kimseunghyun-kr)
 * @version 1.0
 */
public interface BuildingLawFieldsRepository extends JpaRepository<BuildingLawFields, Long> {

    /**
     * Custom query method to find building law fields matching specified classification and specification criteria.
     *
     * @param classification The building classification code.
     * @param specification  The building specification code.
     * @return A list of {@link BuildingLawFields} entities matching the criteria.
     */
    @Query("SELECT blf FROM BuildingLawFields blf " +
            "WHERE (blf.buildingClassification = :classification AND (blf.buildingSpecification = :specification OR blf.buildingSpecification = -1))" +
            "OR (blf.buildingClassification = -1)"
    )
    List<BuildingLawFields> findMatchingPurpose(@Param("classification") Integer classification,
                                                @Param("specification") Integer specification);
}

