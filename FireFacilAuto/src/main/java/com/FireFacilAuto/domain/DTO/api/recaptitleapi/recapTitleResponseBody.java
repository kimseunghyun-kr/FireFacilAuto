package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import lombok.Data;

@Data
public class recapTitleResponseBody {

    private recapTitleItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
