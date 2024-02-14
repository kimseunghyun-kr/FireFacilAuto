package com.FireFacilAuto.domain.DTO.api.floorapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class FloorApiResponse{
    @JsonProperty("response")
    private FloorResponse response;
    // Getters and Setters
}

