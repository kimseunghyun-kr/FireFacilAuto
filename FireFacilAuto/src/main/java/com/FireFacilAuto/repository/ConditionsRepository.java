package com.FireFacilAuto.repository;

import com.FireFacilAuto.domain.Conditions;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The {@code ConditionsRepository} interface provides methods to interact with the database
 * for querying and managing {@link Conditions} entities using Spring Data JPA.
 * <p>
 * It extends {@link JpaRepository} for CRUD operations on the {@link Conditions} entity.
 * </p>
 * <p>
 * This repository is used for handling conditions associated with building and floor law fields.
 * </p>
 *
 * @author trienebutdiene (or kimseunghyun-kr)
 * @version 1.0
 */
public interface ConditionsRepository extends JpaRepository<Conditions, Long> {
    // Additional custom query methods can be added here if needed
}

