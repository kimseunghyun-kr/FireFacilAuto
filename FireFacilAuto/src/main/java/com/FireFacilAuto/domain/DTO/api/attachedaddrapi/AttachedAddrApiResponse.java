package com.FireFacilAuto.domain.DTO.api.attachedaddrapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttachedAddrApiResponse {
    @JsonProperty("response")
    private AttachedAddrResponse response;
    // Getters and Setters
}
