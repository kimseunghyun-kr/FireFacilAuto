package com.FireFacilAuto.domain.installation;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
public class FireServiceSupportDeviceInstallation extends BaseInstallation{
    public Boolean smokeControlApparatus;

    public Boolean hydropPumpApparatus;

    public Boolean waterConnectionApparatus;

    public Boolean emergencyElectricalSupplyApparatus;

    public Boolean wirelessCommunicationsSupportApparatus;

    public Boolean combustionPreventionApparatus;
}
