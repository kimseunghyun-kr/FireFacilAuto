package com.FireFacilAuto.domain.DTO.api.zoningapi;

import lombok.Data;

@Data
public class ZoningResponseBody {

    private ZoningItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
