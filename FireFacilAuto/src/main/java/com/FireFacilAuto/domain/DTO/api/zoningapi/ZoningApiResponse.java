package com.FireFacilAuto.domain.DTO.api.zoningapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class ZoningApiResponse {
    @JsonProperty("response")
    private ZoningResponse response;
    // Getters and Setters
}
