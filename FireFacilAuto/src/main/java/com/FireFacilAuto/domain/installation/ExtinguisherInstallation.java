package com.FireFacilAuto.domain.installation;

import jakarta.persistence.Entity;

@Entity
public class ExtinguisherInstallation extends BaseInstallation {

    public Boolean escapeApparatus;

    public Boolean rescueApparatus;

    public Boolean escapeGuidanceLightingApparatus;

    public Boolean emergencyLightingApparatus;

    public Boolean emergencyHandlightApparatus;

}
