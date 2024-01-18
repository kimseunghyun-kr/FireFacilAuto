package com.FireFacilAuto.repository;


import com.FireFacilAuto.domain.BuildTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildTargetRepository extends JpaRepository<BuildTarget, Long> {
}
