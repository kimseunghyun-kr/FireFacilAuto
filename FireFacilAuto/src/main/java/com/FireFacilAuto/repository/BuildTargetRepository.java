package com.FireFacilAuto.repository;


import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code BuildTargetRepository} interface provides methods to interact with the database
 * for querying and managing {@link Building} entities using Spring Data JPA.
 * <p>
 * It extends {@link JpaRepository} for CRUD operations on the {@link Building} entity.
 * </p>
 * <p>
 * This repository is annotated with {@link Repository} to indicate it as a Spring Data repository.
 * </p>
 *
 * @author trienebutdiene (or kimseunghyun-kr)
 * @version 1.0
 */
@Repository
public interface BuildTargetRepository extends JpaRepository<Building, Long> {
    // Additional custom query methods can be added here if needed
}

