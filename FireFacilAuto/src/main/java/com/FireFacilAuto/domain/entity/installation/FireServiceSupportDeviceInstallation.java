package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class FireServiceSupportDeviceInstallation extends BaseInstallation{
    public Boolean smokeControlApparatus;
    public Boolean hydropPumpApparatus;
    public Boolean waterConnectionApparatus;
    public Boolean emergencyElectricalSupplyApparatus;
    public Boolean wirelessCommunicationsSupportApparatus;
    public Boolean combustionPreventionApparatus;
    public void setBooleanValue(int number) {
        switch (number) {
            case 1 -> setSmokeControlApparatus(true);
            case 2 -> setHydropPumpApparatus(true);
            case 3 -> setWaterConnectionApparatus(true);
            case 4 -> setEmergencyElectricalSupplyApparatus(true);
            case 5 -> setWirelessCommunicationsSupportApparatus(true);
            case 6 -> setCombustionPreventionApparatus(true);
            default -> {
                // Handle invalid number or throw an exception
                throw new IllegalArgumentException("Invalid number: " + number);
            }
        }
    }



}
