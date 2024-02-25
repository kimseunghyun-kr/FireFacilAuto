package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the installation of escape and rescue devices in the system, extending the base installation.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class EscapeRescueInstallation extends BaseInstallation {

    /**
     * Internal system identification code for the escape and rescue installation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Indicates whether an escape apparatus is installed.
     */
    public Boolean escapeApparatus;

    /**
     * Indicates whether a rescue apparatus is installed.
     */
    public Boolean rescueApparatus;

    /**
     * Indicates whether an escape guidance lighting apparatus is installed.
     */
    public Boolean escapeGuidanceLightingApparatus;

    /**
     * Indicates whether an emergency lighting apparatus is installed.
     */
    public Boolean emergencyLightingApparatus;

    /**
     * Indicates whether an emergency handlight apparatus is installed.
     */
    public Boolean emergencyHandlightApparatus;

    /**
     * Sets the boolean value based on the specified number.
     *
     * @param number The number corresponding to the boolean value to be set.
     *               1: escapeApparatus, 2: rescueApparatus, 3: escapeGuidanceLightingApparatus,
     *               4: emergencyLightingApparatus, 5: emergencyHandlightApparatus
     *               // Add more cases for additional properties as needed
     * @throws IllegalArgumentException if the number is invalid.
     */
    @Override
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

    /**
     * Gets the total number of fields in the class.
     *
     * @return The total number of fields.
     */
    public static Integer getTotalFieldSize() {
        return 5;
    }
}

