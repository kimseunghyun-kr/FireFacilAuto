package com.FireFacilAuto.service;

import com.FireFacilAuto.domain.DTO.InputDTO;
import com.FireFacilAuto.domain.entity.BuildTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainService {

    private final AlarmDeviceClassifier alarmDeviceClassifier;
    private final EscapeRescueClassifier escapeRescueClassifier;
    private final ExtinguisherClassifier extinguisherClassifier;
    private final FireServiceSupportDeviceClassifier fireServiceSupportDeviceClassifier;
    private final GeneralBuildUsageClassifier generalBuildUsageClassifier;
    @Autowired
    public MainService(AlarmDeviceClassifier alarmDeviceClassifier, EscapeRescueClassifier escapeRescueClassifier, ExtinguisherClassifier extinguisherClassifier, FireServiceSupportDeviceClassifier fireServiceSupportDeviceClassifier, GeneralBuildUsageClassifier generalBuildUsageClassifier) {
        this.alarmDeviceClassifier = alarmDeviceClassifier;
        this.escapeRescueClassifier = escapeRescueClassifier;
        this.extinguisherClassifier = extinguisherClassifier;
        this.fireServiceSupportDeviceClassifier = fireServiceSupportDeviceClassifier;
        this.generalBuildUsageClassifier = generalBuildUsageClassifier;
    }

    public BuildTarget execute(InputDTO inputDTO) {
        BuildTarget buildTarget = this.inputMapper(inputDTO);
        buildTarget.extinguisherInstallation = extinguisherClassifier.classify(buildTarget);

        return buildTarget;
    }

    public BuildTarget inputMapper (InputDTO inputDTO) {
        BuildTarget buildTarget = new BuildTarget();
        buildTarget.juso = inputDTO.juso;
        buildTarget.GFA = inputDTO.GFA;
        buildTarget.floor = inputDTO.floor;
        buildTarget.classification = inputDTO.classification.getNumber();
        buildTarget.dateOfConstruction = inputDTO.dateOfConstruction;
        return buildTarget;
    }




}
