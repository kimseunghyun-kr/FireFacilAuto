package com.FireFacilAuto.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

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
