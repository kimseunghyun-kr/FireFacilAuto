package com.FireFacilAuto.domain.entity.results;

import com.FireFacilAuto.domain.entity.floors.Floor;
import com.FireFacilAuto.domain.entity.installation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * Represents fire safety installations associated with a specific floor.
 * This class is designed to simplify the management of fire safety installations for each floor.
 */
@Data
@Entity
public class FloorResults {

    /** Identifier for the FloorResults entity. */
    @Id
    private Long id;

    /** Associated floor in the building. */
    @OneToOne
    private Floor floor;

    /** Installation for extinguishing fires. */
    @OneToOne
    private ExtinguisherInstallation extinguisherInstallation;

    /** Installation for alarm devices. */
    @OneToOne
    private AlarmDeviceInstallation alarmDeviceInstallation;

    /** Installation for escape and rescue. */
    @OneToOne
    private EscapeRescueInstallation escapeRescueInstallation;

    /** Installation for fire service support devices. */
    @OneToOne
    private FireServiceSupportDeviceInstallation fireServiceSupportDeviceInstallation;

    /** Installation for water supply. */
    @OneToOne
    private WaterSupplyInstallation waterSupplyInstallation;

    /**
     * Static method to create a new instance of {@code FloorResults} and initialize associated installations.
     *
     * @param floor The associated floor entity.
     * @return A new instance of {@code FloorResults}.
     */
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

    /**
     * Sets boolean values for installations based on numeric parameters.
     *
     * @param current The current installation identifier.
     * @param child   The numeric value to set for the installation.
     */
    public void numericSetter(int current, int child) {
        switch (current) {
            case 1 -> extinguisherInstallation.setBooleanValue(child);
            case 2 -> alarmDeviceInstallation.setBooleanValue(child);
            case 3 -> fireServiceSupportDeviceInstallation.setBooleanValue(child);
            case 4 -> waterSupplyInstallation.setBooleanValue(child);
            case 5 -> escapeRescueInstallation.setBooleanValue(child);
        }
    }
}

