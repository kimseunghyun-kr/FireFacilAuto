package com.FireFacilAuto.domain.DTO.api.watercatchmentfiltrationapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WaterCatchmentFiltrationApiResponse {
    @JsonProperty("response")
    private WaterCatchmentFiltrationResponse response;
    // Getters and Setters
}
