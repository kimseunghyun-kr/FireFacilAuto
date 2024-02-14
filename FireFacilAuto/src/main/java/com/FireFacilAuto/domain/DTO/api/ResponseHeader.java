package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseHeader {

    @JsonProperty("resultCode")
    private String resultCode;

    @JsonProperty("resultMsg")
    private String resultMsg;

    // Getters and Setters
}
