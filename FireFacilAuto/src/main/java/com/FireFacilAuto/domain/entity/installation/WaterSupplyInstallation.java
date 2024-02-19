package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class WaterSupplyInstallation extends BaseInstallation {
    public Boolean extinguisherWaterSupplyInstallation;

    public void setBooleanValue(int number) {
        if (number == 1) {
            setExtinguisherWaterSupplyInstallation(true);
        } else {// Handle invalid number or throw an exception
            throw new IllegalArgumentException("Invalid number: " + number);
        }
    }

}
