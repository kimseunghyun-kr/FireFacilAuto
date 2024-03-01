package com.FireFacilAuto.service.lawService.buildinglaws;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.repository.BuildingLawFieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingClassification;
import static com.FireFacilAuto.domain.entity.building.BuildingUtils.getBuildingSpecification;

@Service
public class BuildingLawRepositoryService {
    private final BuildingLawFieldsRepository blawFieldRepository;

    @Autowired
    public BuildingLawRepositoryService(BuildingLawFieldsRepository blawFieldRepository) {
        this.blawFieldRepository = blawFieldRepository;
    }

    public void save(BuildingLawFields blawFields) {
        blawFieldRepository.save(blawFields);
    }

    @SuppressWarnings("unchecked")
    public Page<BuildingLawFields> getPaginatedLaws(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return blawFieldRepository.findAll(pageable);
    }

    public List<BuildingLawFields> getLawsWithApplicablePurpose(Building building) {
        return blawFieldRepository.findMatchingPurpose(
                getBuildingClassification(building),
                getBuildingSpecification(building)
        );
    }


}
