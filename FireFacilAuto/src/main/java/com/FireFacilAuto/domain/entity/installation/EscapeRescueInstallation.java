package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class EscapeRescueInstallation extends BaseInstallation{
    public Boolean escapeApparatus;
    public Boolean rescueApparatus;
    public Boolean escapeGuidanceLightingApparatus;
    public Boolean emergencyLightingApparatus;
    public Boolean emergencyHandlightApparatus;

    public void setBooleanValue(int number) {
        switch (number) {
            case 1 -> setEscapeApparatus(true);
            case 2 -> setRescueApparatus(true);
            case 3 -> setEscapeGuidanceLightingApparatus(true);
            case 4 -> setEmergencyLightingApparatus(true);
            case 5 -> setEmergencyHandlightApparatus(true);
            // Add more cases for additional properties as needed
            default -> {
                // Handle invalid number or throw an exception
                throw new IllegalArgumentException("Invalid number: " + number);
            }
        }
    }
}
