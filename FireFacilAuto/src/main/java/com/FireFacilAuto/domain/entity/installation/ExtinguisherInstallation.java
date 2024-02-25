package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the installation of various fire extinguishing devices in the system, extending the base installation.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtinguisherInstallation extends BaseInstallation {

    /**
     * Internal system identification code for the extinguisher installation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Indicates whether a general extinguisher apparatus is installed.
     */
    public Boolean extinguisherApparatus;

    /**
     * Indicates whether an automatic fire extinguisher apparatus with human activation is installed.
     */
    public Boolean HUAutomaticFireExtinguisherApparatus;

    /**
     * Indicates whether an automatic fire extinguisher apparatus with central activation is installed.
     */
    public Boolean CEAutomaticFireExtinguisherApparatus;

    /**
     * Indicates whether an indoor fire hydrant apparatus is installed.
     */
    public Boolean IndoorFireHydrantApparatus;

    /**
     * Indicates whether an outdoor fire hydrant apparatus is installed.
     */
    public Boolean OutdoorFireHydrantApparatus;

    /**
     * Indicates whether a sprinkler apparatus is installed.
     */
    public Boolean SprinklerApparatus;

    /**
     * Indicates whether a simplified sprinkler apparatus is installed.
     */
    public Boolean SimplfiedSprinklerApparatus;

    /**
     * Indicates whether a water sprayer, etc. apparatus is installed.
     */
    public Boolean waterSprayerEtCeteraApparatus;

    /**
     * Sets the boolean value based on the specified number.
     *
     * @param number The number corresponding to the boolean value to be set.
     *               1: extinguisherApparatus, 2: HUAutomaticFireExtinguisherApparatus,
     *               3: CEAutomaticFireExtinguisherApparatus, 4: IndoorFireHydrantApparatus,
     *               5: OutdoorFireHydrantApparatus, 6: SprinklerApparatus, 7: SimplfiedSprinklerApparatus,
     *               8: waterSprayerEtCeteraApparatus
     * @throws IllegalArgumentException if the number is invalid.
     */
    @Override
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

    /**
     * Gets the total number of fields in the class.
     *
     * @return The total number of fields.
     */
    public static Integer getTotalFieldSize() {
        return 8;
    }
}
