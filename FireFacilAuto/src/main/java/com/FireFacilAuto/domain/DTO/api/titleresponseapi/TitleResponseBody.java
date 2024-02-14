package com.FireFacilAuto.domain.DTO.api.titleresponseapi;

import lombok.Data;

@Data
public class TitleResponseBody {

    private TitleItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
