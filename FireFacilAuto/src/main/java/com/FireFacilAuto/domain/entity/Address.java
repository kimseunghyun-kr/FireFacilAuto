package com.FireFacilAuto.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class Address implements Serializable {

    private String detailAdr;
    private String streetAdr;
    private String zipCode;
    private String extraAdr;
    private String sigunguCode;
    private String bcode;

    private String ji;
    private String bun;
    // getters and setters

}
