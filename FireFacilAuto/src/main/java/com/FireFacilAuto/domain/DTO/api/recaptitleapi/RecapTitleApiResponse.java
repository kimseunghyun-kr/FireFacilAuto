package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class RecapTitleApiResponse {
    @JsonProperty("response")
    private RecapTitleResponse response;
    // Getters and Setters
}

