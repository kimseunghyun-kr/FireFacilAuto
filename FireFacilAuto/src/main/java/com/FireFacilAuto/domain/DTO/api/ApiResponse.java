package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ApiResponse<T extends ApiResponseItem> {
    @JsonProperty("response")
    private Response<T> response;
    // Getters and Setters
}

