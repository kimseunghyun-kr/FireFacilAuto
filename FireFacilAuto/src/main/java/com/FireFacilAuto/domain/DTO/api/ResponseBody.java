package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseBody<T extends ApiResponseItem> {

    private Items<T> items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    // Getters and Setters
}
