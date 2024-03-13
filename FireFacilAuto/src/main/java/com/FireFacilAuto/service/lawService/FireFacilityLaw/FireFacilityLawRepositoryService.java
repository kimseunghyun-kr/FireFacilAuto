package com.FireFacilAuto.service.lawService.FireFacilityLaw;


import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.FireFacilityLaw;
import com.FireFacilAuto.repository.FireFacilityLawRepository;
import com.FireFacilAuto.util.records.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingClassification;
import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingSpecification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorClassification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorSpecification;

@Service
@Slf4j
public class FireFacilityLawRepositoryService {
    private final FireFacilityLawRepository blawFieldRepository;

    @Autowired
    public FireFacilityLawRepositoryService(FireFacilityLawRepository blawFieldRepository) {
        this.blawFieldRepository = blawFieldRepository;
    }

    public void save(FireFacilityLaw blaw) {
        blawFieldRepository.save(blaw);
    }

    @SuppressWarnings("unchecked")
    public Page<FireFacilityLaw> getPaginatedLaws(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blawFieldRepository.findAll(pageable);
    }

    public List<FireFacilityLaw> getLawsWithApplicablePurpose(Building building) {
        Set<Pair> setOfAllPurposes = new HashSet<>();

        Pair buildingPurposePair = new Pair(getBuildingClassification(building), getBuildingSpecification(building));
        setOfAllPurposes.add(buildingPurposePair);
        List<FireFacilityLaw> blawList = blawFieldRepository.findMatchingPurpose(buildingPurposePair.first(), buildingPurposePair.second());

        Set<FireFacilityLaw> candidateLawSet = new HashSet<>(blawList);
        for (Floor floor : building.getCompositeFloorsList()) {
            Pair floorPurposePair = new Pair(getFloorClassification(floor), getFloorSpecification(floor));
            if(setOfAllPurposes.add(floorPurposePair)) {
                candidateLawSet.addAll(blawFieldRepository.findMatchingPurpose(floorPurposePair.first(),floorPurposePair.second()));
            }
        }
        return candidateLawSet.stream().toList();
    }
}
