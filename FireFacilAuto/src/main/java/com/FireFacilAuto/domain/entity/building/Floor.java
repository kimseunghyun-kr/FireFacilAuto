package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.building.Building;
import com.FireFacilAuto.domain.entity.installation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Floor {
    @Id
    public Long UUID; //내부 시스템용 식별코드

    @ManyToOne
    public Building building; //연관된 건물

    public Integer floorNo; //충수

    public Boolean isUnderGround; //지하여부

    public Integer floorClassification; //층 주용도

    public Integer floorSpecification; //층 세부용도

    public Double floorArea; //층 바닥면적

    public Integer floorMaterial; //층 재료

//    아직 미포함인 정보

    public Boolean floorWindowAvailability; //무창층

    @OneToOne
    public AlarmDeviceInstallation alarmDeviceInstallation;

    @OneToOne
    public EscapeRescueInstallation escapeRescueInstallation;

    @OneToOne
    public ExtinguisherInstallation extinguisherInstallation;

    @OneToOne
    public WaterSupplyInstallation waterSupplyInstallation;

    @OneToOne
    public FireServiceSupportDeviceInstallation fireServiceSupportInstallation;
}
