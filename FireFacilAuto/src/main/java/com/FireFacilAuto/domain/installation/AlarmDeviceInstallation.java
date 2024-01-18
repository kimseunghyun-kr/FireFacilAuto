package com.FireFacilAuto.domain.installation;

import jakarta.persistence.Entity;

@Entity
public class AlarmDeviceInstallation extends BaseInstallation {
    public Boolean extinguisherApparatus;

    public Boolean IndoorFireHydrantApparatus;

    public Boolean OutdoorFireHydrantApparatus;

    public Boolean SprinklerApparatus;

    public Boolean SimplfiedSprinklerApparatus;

    public Boolean waterSprayerEtCeteraApparatus;

}
