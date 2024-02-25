package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder;

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
    LocalDate randomDate;

    public BuildingAttributes() {
    }

    private BuildingAttributes(Boolean flag) {
        this.setManaualBuildFlag(false);
    }

    public BuildingAttributes(int bhc, int bc, int bs, int bm, int ugf, int ogf, double gfa, LocalDate randomDate) {
        this.bhc = bhc;
        this.bc = bc;
        this.bs = bs;
        this.bm = bm;
        this.ugf = ugf;
        this.ogf = ogf;
        this.tf = ugf + ogf;
        this.gfa = gfa;
        this.randomDate = randomDate;
    }

    public static BuildingAttributes automatedBuild() {
        return automatedBuild;
    }

}
