package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class AlarmDeviceInstallation extends BaseInstallation {

    public Boolean AutoFireDectionApparatus;
    public Boolean EmergencyAlarmApparatus;
    public Boolean EmergencyBroadcastApparatus;
    public Boolean ElectricalLeakageAlarmApparatus;
    public Boolean AutomatedEmergencyServiceAlerterApparatus;
    public Boolean IndividualOperatingDetectionApparatus;
    public Boolean VisualAlarmApparatus;
    public Boolean GasLeakageAlarmApparatus;

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

}
