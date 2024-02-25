package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the installation of fire service support devices in the system, extending the base installation.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class FireServiceSupportDeviceInstallation extends BaseInstallation {

    /**
     * Internal system identification code for the fire service support device installation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Indicates whether a smoke control apparatus is installed.
     */
    public Boolean smokeControlApparatus;

    /**
     * Indicates whether a hydropump apparatus is installed.
     */
    public Boolean hydropPumpApparatus;

    /**
     * Indicates whether a water connection apparatus is installed.
     */
    public Boolean waterConnectionApparatus;

    /**
     * Indicates whether an emergency electrical supply apparatus is installed.
     */
    public Boolean emergencyElectricalSupplyApparatus;

    /**
     * Indicates whether a wireless communications support apparatus is installed.
     */
    public Boolean wirelessCommunicationsSupportApparatus;

    /**
     * Indicates whether a combustion prevention apparatus is installed.
     */
    public Boolean combustionPreventionApparatus;

    /**
     * Sets the boolean value based on the specified number.
     *
     * @param number The number corresponding to the boolean value to be set.
     *               1: smokeControlApparatus, 2: hydropPumpApparatus, 3: waterConnectionApparatus,
     *               4: emergencyElectricalSupplyApparatus, 5: wirelessCommunicationsSupportApparatus,
     *               6: combustionPreventionApparatus
     * @throws IllegalArgumentException if the number is invalid.
     */
    @Override
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

    /**
     * Gets the total number of fields in the class.
     *
     * @return The total number of fields.
     */
    public static Integer getTotalFieldSize() {
        return 6;
    }
}

