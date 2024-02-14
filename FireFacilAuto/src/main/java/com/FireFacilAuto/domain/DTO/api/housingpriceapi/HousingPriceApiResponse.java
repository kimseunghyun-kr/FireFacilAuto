package com.FireFacilAuto.domain.DTO.api.housingpriceapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HousingPriceApiResponse {
    @JsonProperty("response")
    private HousingPriceResponse response;
    // Getters and Setters
}
