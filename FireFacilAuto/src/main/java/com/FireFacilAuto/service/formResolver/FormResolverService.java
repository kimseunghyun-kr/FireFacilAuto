package com.FireFacilAuto.service.formResolver;

import com.FireFacilAuto.domain.DTO.form.FormBuildingDTO;
import com.FireFacilAuto.domain.entity.building.Building;
import org.springframework.stereotype.Service;


@Service
public class FormResolverService {

    public Building mapToBuilding (FormBuildingDTO inputDTO) {
        Building buildTarget = new Building();
        buildTarget.juso = inputDTO.juso;
        buildTarget.GFA = inputDTO.GFA;
        buildTarget.totalFloors = inputDTO.floor;
        buildTarget.buildingClassification = inputDTO.classification;
        buildTarget.dateofApproval = inputDTO.dateOfApproval;
        return buildTarget;
    }


}
