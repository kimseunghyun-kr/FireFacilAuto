package com.FireFacilAuto.domain.installation;

import jakarta.persistence.Entity;

@Entity
public class EscapeRescueInstallation extends BaseInstallation{

    public Boolean AutoFireDectionApparatus;

    public Boolean EmergencyAlarmApparatus;

    public Boolean EmergencyBroadcastApparatus;

    public Boolean ElectricalLeakageAlarmApparatus;

    public Boolean AutomatedEmergencyServiceAlerterApparatus;

    public Boolean IndividualOperatingDetectionApparatus;

    public Boolean VisualAlarmApparatus;
    public Boolean GasLeakageAlarmApparatus;
}
