package com.FireFacilAuto.service;

import com.FireFacilAuto.service.classificationServices.*;
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





}
