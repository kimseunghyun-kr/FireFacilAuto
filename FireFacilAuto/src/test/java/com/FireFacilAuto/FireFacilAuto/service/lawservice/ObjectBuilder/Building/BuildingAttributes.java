package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BuildingAttributes {

    private static BuildingAttributes automatedBuild = new BuildingAttributes(false);
    boolean manaualBuildFlag;
    int bhc;
    int bc;
    int bs;
    int bm;
    int ugf;
    int ogf;
    int tf;
    double gfa;
    LocalDate localdate;

    public BuildingAttributes() {
    }

    private BuildingAttributes(Boolean flag) {
        this.setManaualBuildFlag(false);
    }

    public BuildingAttributes(int bhc, int bc, int bs, int bm, int ugf, int ogf, double gfa, LocalDate localdate , boolean manualBuildFlag) {
        this.bhc = bhc;
        this.bc = bc;
        this.bs = bs;
        this.bm = bm;
        this.ugf = ugf;
        this.ogf = ogf;
        this.tf = ugf + ogf;
        this.gfa = gfa;
        this.localdate = localdate;
        this.manaualBuildFlag = manualBuildFlag;
    }

    public static BuildingAttributes automatedBuild() {
        return automatedBuild;
    }

}
