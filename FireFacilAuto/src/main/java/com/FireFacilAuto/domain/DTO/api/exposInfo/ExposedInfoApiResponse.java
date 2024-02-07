package com.FireFacilAuto.domain.DTO.api.exposInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExposedInfoApiResponse {
    @JsonProperty("response")
    private ExposedInfoResponse response;
    // Getters and Setters
}
