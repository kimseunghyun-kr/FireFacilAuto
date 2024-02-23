package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AlarmDeviceInstallation extends BaseInstallation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    public Boolean AutoFireDectionApparatus;
    public Boolean EmergencyAlarmApparatus;
    public Boolean EmergencyBroadcastApparatus;
    public Boolean ElectricalLeakageAlarmApparatus;
    public Boolean AutomatedEmergencyServiceAlerterApparatus;
    public Boolean IndividualOperatingDetectionApparatus;
    public Boolean VisualAlarmApparatus;
    public Boolean GasLeakageAlarmApparatus;

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

}
