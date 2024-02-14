package com.FireFacilAuto.domain.DTO.api.floorapi;

import lombok.Data;

@Data
public class FloorResponseBody {

    private FloorItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
