package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;

@Entity
public class EscapeRescueInstallation extends BaseInstallation{
    public Boolean escapeApparatus;
    public Boolean rescueApparatus;
    public Boolean escapeGuidanceLightingApparatus;
    public Boolean emergencyLightingApparatus;
    public Boolean emergencyHandlightApparatus;
}
