package com.FireFacilAuto.domain.DTO.api.attachedaddrapi;

import lombok.Data;

@Data
public class AttachedAddrResponseBody {

    private AttachedAddrItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
