package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtinguisherInstallation extends BaseInstallation {
    public Boolean extinguisherApparatus;
    public Boolean HUAutomaticFireExtinguisherApparatus;
    public Boolean CEAutomaticFireExtinguisherApparatus;
    public Boolean IndoorFireHydrantApparatus;
    public Boolean OutdoorFireHydrantApparatus;
    public Boolean SprinklerApparatus;
    public Boolean SimplfiedSprinklerApparatus;
    public Boolean waterSprayerEtCeteraApparatus;


    public void setBooleanValue(int number) {
        switch (number) {
            case 1 -> setExtinguisherApparatus(true);
            case 2 -> setHUAutomaticFireExtinguisherApparatus(true);
            case 3 -> setCEAutomaticFireExtinguisherApparatus(true);
            case 4 -> setIndoorFireHydrantApparatus(true);
            case 5 -> setOutdoorFireHydrantApparatus(true);
            case 6 -> setSprinklerApparatus(true);
            case 7 -> setSimplfiedSprinklerApparatus(true);
            case 8 -> setWaterSprayerEtCeteraApparatus(true);
            default -> {
                // Handle invalid number or throw an exception
                throw new IllegalArgumentException("Invalid number: " + number);
            }
        }
    }


}
