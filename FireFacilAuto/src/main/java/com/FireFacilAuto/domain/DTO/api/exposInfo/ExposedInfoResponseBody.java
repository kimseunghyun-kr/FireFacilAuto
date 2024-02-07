package com.FireFacilAuto.domain.DTO.api.exposInfo;

import lombok.Data;

@Data
public class ExposedInfoResponseBody {

    private ExposedInfoItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
