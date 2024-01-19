package com.FireFacilAuto.service;

import com.FireFacilAuto.domain.entity.BuildTarget;
import com.FireFacilAuto.domain.entity.installation.ExtinguisherInstallation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExtinguisherClassifier {
    private final List<Integer> extingList = new ArrayList<Integer>(Arrays.asList(17, 23, 27, 28, 29));
    public ExtinguisherInstallation classify(BuildTarget buildTarget) {
        ExtinguisherInstallation extinguisherInstallation = new ExtinguisherInstallation();
        extinguisherInstallation.extinguisherApparatus = extinguisherApparatusCheck(buildTarget);




        return extinguisherInstallation;
    }

    public Boolean extinguisherApparatusCheck(BuildTarget bt) {
        if(bt.GFA >= 33){
            return true;
        }
        if(extingList.contains(bt.classification)){
            return true;
        }
        return false;
    }

    public Boolean indoorFireHydrantApparatusCheck(BuildTarget bt){

    }

}
