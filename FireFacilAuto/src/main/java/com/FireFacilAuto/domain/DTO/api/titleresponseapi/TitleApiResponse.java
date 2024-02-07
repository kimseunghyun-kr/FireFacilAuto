package com.FireFacilAuto.domain.DTO.api.titleresponseapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TitleApiResponse {
    @JsonProperty("response")
    private TitleResponse response;
    // Getters and Setters
}
