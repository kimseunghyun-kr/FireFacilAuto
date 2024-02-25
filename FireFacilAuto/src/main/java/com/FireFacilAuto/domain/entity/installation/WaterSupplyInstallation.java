package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the installation of water supply for fire extinguishing in the system, extending the base installation.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class WaterSupplyInstallation extends BaseInstallation {

    /**
     * Internal system identification code for the water supply installation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    /**
     * Indicates whether an extinguisher water supply installation is present.
     */
    public Boolean extinguisherWaterSupplyInstallation;

    /**
     * Sets the boolean value based on the specified number.
     *
     * @param number The number corresponding to the boolean value to be set.
     *               1: extinguisherWaterSupplyInstallation
     * @throws IllegalArgumentException if the number is invalid.
     */
    @Override
    public void setBooleanValue(int number) {
        if (number == 1) {
            setExtinguisherWaterSupplyInstallation(true);
        } else {
            // Handle invalid number or throw an exception
            throw new IllegalArgumentException("Invalid number: " + number);
        }
    }

    /**
     * Gets the total number of fields in the class.
     *
     * @return The total number of fields.
     */
    public static Integer getTotalFieldSize() {
        return 1;
    }
}
