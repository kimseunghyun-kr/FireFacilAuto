package com.FireFacilAuto.domain.entity.results;

import com.FireFacilAuto.domain.entity.building.Floor;
import com.FireFacilAuto.domain.entity.installation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class FloorResults {

    @Id
    private Long id;

    @OneToOne
    private Floor floor;

    @OneToOne
    private ExtinguisherInstallation extinguisherInstallation;

    @OneToOne
    private AlarmDeviceInstallation alarmDeviceInstallation;

    @OneToOne
    private EscapeRescueInstallation escapeRescueInstallation;

    @OneToOne
    private FireServiceSupportDeviceInstallation fireServiceSupportDeviceInstallation;

    @OneToOne
    private WaterSupplyInstallation waterSupplyInstallation;

    public static FloorResults floorFactory(Floor floor) {
        FloorResults floorResults = new FloorResults();

        floorResults.floor = floor;
        floorResults.extinguisherInstallation = new ExtinguisherInstallation();
        floorResults.alarmDeviceInstallation = new AlarmDeviceInstallation();
        floorResults.escapeRescueInstallation = new EscapeRescueInstallation();
        floorResults.fireServiceSupportDeviceInstallation = new FireServiceSupportDeviceInstallation();
        floorResults.waterSupplyInstallation = new WaterSupplyInstallation();

        return floorResults;
    }

    public void numericSetter (int current, int child) {
        switch (current) {
            case 1 -> this.getExtinguisherInstallation().setBooleanValue(child);
            case 2 -> this.getAlarmDeviceInstallation().setBooleanValue(child);
            case 3 -> this.getFireServiceSupportDeviceInstallation().setBooleanValue(child);
            case 4 -> this.getWaterSupplyInstallation().setBooleanValue(child);
            case 5 -> this.getEscapeRescueInstallation().setBooleanValue(child);
        }
    }

}
