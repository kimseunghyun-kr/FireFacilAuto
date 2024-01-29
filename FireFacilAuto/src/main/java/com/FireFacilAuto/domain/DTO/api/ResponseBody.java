package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseBody {

    @JsonProperty("items")
    private List<ApiResponseItem> items;

    // Getters and Setters
}
