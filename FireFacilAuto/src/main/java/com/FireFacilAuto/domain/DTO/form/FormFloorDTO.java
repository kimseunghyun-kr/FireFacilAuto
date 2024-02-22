package com.FireFacilAuto.domain.DTO.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class FormFloorDTO implements Serializable {

    public Integer floorNo; //충수

    public Boolean isUnderground; //지하여부

    public Integer floorClassification; //층 주용도

    public Integer floorSpecification; //층 세부용도

    public Double floorArea; //층 바닥면적

    public Integer floorMaterial; //층 재료

    //    아직 미포함인 정보
    public Boolean floorWindowAvailability; //무창층

}
