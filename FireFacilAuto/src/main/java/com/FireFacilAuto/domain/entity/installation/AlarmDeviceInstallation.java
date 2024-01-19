package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;

@Entity
public class AlarmDeviceInstallation extends BaseInstallation {
    public Boolean AutoFireDectionApparatus;
    public Boolean EmergencyAlarmApparatus;
    public Boolean EmergencyBroadcastApparatus;
    public Boolean ElectricalLeakageAlarmApparatus;
    public Boolean AutomatedEmergencyServiceAlerterApparatus;
    public Boolean IndividualOperatingDetectionApparatus;
    public Boolean VisualAlarmApparatus;
    public Boolean GasLeakageAlarmApparatus;
}
