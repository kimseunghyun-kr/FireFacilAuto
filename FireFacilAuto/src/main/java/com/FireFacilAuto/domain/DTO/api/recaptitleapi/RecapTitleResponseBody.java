package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecapTitleResponseBody {

    @JsonProperty("items")
    private RecapTitleItems items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
