package com.FireFacilAuto.domain.entity.installation;

import com.FireFacilAuto.domain.entity.building.Floor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class WaterSupplyInstallation extends BaseInstallation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long UUID;

    public Boolean extinguisherWaterSupplyInstallation;

    public void setBooleanValue(int number) {
        if (number == 1) {
            setExtinguisherWaterSupplyInstallation(true);
        } else {// Handle invalid number or throw an exception
            throw new IllegalArgumentException("Invalid number: " + number);
        }
    }

}
