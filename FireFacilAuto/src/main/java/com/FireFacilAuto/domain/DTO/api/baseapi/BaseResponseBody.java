package com.FireFacilAuto.domain.DTO.api.baseapi;

import lombok.Data;

@Data
public class BaseResponseBody {

    private BaseItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
