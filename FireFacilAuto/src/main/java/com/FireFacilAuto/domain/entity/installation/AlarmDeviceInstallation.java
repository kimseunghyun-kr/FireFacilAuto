package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the installation of alarm devices in the system, extending the base installation.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class AlarmDeviceInstallation extends BaseInstallation {

    /**
     * Internal system identification code for the alarm device installation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Indicates whether an auto-fire detection apparatus is installed.
     */
    public Boolean AutoFireDectionApparatus;

    /**
     * Indicates whether an emergency alarm apparatus is installed.
     */
    public Boolean EmergencyAlarmApparatus;

    /**
     * Indicates whether an emergency broadcast apparatus is installed.
     */
    public Boolean EmergencyBroadcastApparatus;

    /**
     * Indicates whether an electrical leakage alarm apparatus is installed.
     */
    public Boolean ElectricalLeakageAlarmApparatus;

    /**
     * Indicates whether an automated emergency service alerter apparatus is installed.
     */
    public Boolean AutomatedEmergencyServiceAlerterApparatus;

    /**
     * Indicates whether an individual operating detection apparatus is installed.
     */
    public Boolean IndividualOperatingDetectionApparatus;

    /**
     * Indicates whether a visual alarm apparatus is installed.
     */
    public Boolean VisualAlarmApparatus;

    /**
     * Indicates whether a gas leakage alarm apparatus is installed.
     */
    public Boolean GasLeakageAlarmApparatus;

    /**
     * Sets the boolean value based on the specified number.
     *
     * @param number The number corresponding to the boolean value to be set.
     *               1: AutoFireDectionApparatus, 2: EmergencyAlarmApparatus, 3: EmergencyBroadcastApparatus,
     *               4: ElectricalLeakageAlarmApparatus, 5: AutomatedEmergencyServiceAlerterApparatus,
     *               6: IndividualOperatingDetectionApparatus, 7: VisualAlarmApparatus, 8: GasLeakageAlarmApparatus
     * @throws IllegalArgumentException if the number is invalid.
     */
    @Override
    public void setBooleanValue(int number) {
        switch (number) {
            case 1 -> setAutoFireDectionApparatus(true);
            case 2 -> setEmergencyAlarmApparatus(true);
            case 3 -> setEmergencyBroadcastApparatus(true);
            case 4 -> setElectricalLeakageAlarmApparatus(true);
            case 5 -> setAutomatedEmergencyServiceAlerterApparatus(true);
            case 6 -> setIndividualOperatingDetectionApparatus(true);
            case 7 -> setVisualAlarmApparatus(true);
            case 8 -> setGasLeakageAlarmApparatus(true);
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
        return 8;
    }
}
