package com.FireFacilAuto.domain.DTO.api.watercatchmentfiltrationapi;

import lombok.Data;

@Data
public class WaterCatchmentFiltrationResponseBody {

    private WaterCatchmentFiltrationItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
