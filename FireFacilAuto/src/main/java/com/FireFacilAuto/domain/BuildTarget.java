package com.FireFacilAuto.domain;

import com.FireFacilAuto.domain.installation.AlarmDeviceInstallation;
import com.FireFacilAuto.domain.installation.EscapeRescueInstallation;
import com.FireFacilAuto.domain.installation.ExtinguisherInstallation;
import com.FireFacilAuto.domain.installation.FireServiceSupportDeviceInstallation;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BuildTarget {

    @Id
    public Long UUID;

    @Embedded
    public Address juso;

    public Integer floor;

    public Long GFA;

    public Integer classification;

    public LocalDateTime dateOfConstruction;

    @OneToOne
    public AlarmDeviceInstallation alarmDeviceInstallation;

    @OneToOne
    public EscapeRescueInstallation escapeRescueInstallation;

    @OneToOne
    public ExtinguisherInstallation extinguisherInstallation;

    @OneToOne
    public FireServiceSupportDeviceInstallation fireServiceSupportInstallation;

}
