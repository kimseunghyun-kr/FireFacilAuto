package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;

@Entity
public class FireServiceSupportDeviceInstallation extends BaseInstallation{
    public Boolean smokeControlApparatus;
    public Boolean hydropPumpApparatus;
    public Boolean waterConnectionApparatus;
    public Boolean emergencyElectricalSupplyApparatus;
    public Boolean wirelessCommunicationsSupportApparatus;
    public Boolean combustionPreventionApparatus;
}
