package com.FireFacilAuto.domain.DTO.api.baseapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class BaseApiResponse {
    @JsonProperty("response")
    private BaseResponse response;
    // Getters and Setters
}

