package com.FireFacilAuto.repository;


import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildTargetRepository extends JpaRepository<Building, Long> {
}
