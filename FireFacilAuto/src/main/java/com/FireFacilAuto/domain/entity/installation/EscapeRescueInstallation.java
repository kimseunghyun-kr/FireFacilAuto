package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class EscapeRescueInstallation extends BaseInstallation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

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
