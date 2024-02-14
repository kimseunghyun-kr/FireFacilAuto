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
    Floor floor;

    @OneToOne
    ExtinguisherInstallation extinguisherInstallation;

    @OneToOne
    AlarmDeviceInstallation alarmDeviceInstallation;

    @OneToOne
    EscapeRescueInstallation escapeRescueInstallation;

    @OneToOne
    FireServiceSupportDeviceInstallation fireServiceSupportDeviceInstallation;

    @OneToOne
    WaterSupplyInstallation waterSupplyInstallation;

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

}
