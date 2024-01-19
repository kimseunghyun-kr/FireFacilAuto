package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;

@Entity
public class ExtinguisherInstallation extends BaseInstallation {
    public Boolean extinguisherApparatus;
    public Boolean IndoorFireHydrantApparatus;
    public Boolean OutdoorFireHydrantApparatus;
    public Boolean SprinklerApparatus;
    public Boolean SimplfiedSprinklerApparatus;
    public Boolean waterSprayerEtCeteraApparatus;

}
