package com.FireFacilAuto.service.lawService.floorLaws;

import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import com.FireFacilAuto.repository.FloorLawFieldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorClassification;
import static com.FireFacilAuto.domain.entity.floors.FloorUtils.getFloorSpecification;

@Service
public class FloorLawRepositoryService {
    private final FloorLawFieldsRepository flawFieldRepository;

    @Autowired
    public FloorLawRepositoryService(FloorLawFieldsRepository flawFieldRepository) {
        this.flawFieldRepository = flawFieldRepository;
    }

    public void save(FloorLawFields flawField) {
        flawFieldRepository.save(flawField);
    }

    @SuppressWarnings("unchecked")
    public Page<FloorLawFields> getPaginatedLaws(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return flawFieldRepository.findAll(pageable);
    }

    public List<FloorLawFields> getLawsWithApplicablePurpose(Floor floor) {
        return flawFieldRepository.findMatchingPurpose(
                getFloorClassification(floor),
                getFloorSpecification(floor)
        );
    }


}
