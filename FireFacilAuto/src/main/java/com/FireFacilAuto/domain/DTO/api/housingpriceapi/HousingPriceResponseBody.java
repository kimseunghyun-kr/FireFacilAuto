package com.FireFacilAuto.domain.DTO.api.housingpriceapi;

import lombok.Data;

@Data
public class HousingPriceResponseBody {

    private HousingPriceItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
