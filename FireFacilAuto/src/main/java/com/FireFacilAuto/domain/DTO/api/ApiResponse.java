package com.FireFacilAuto.domain.DTO.api;

import lombok.Data;


@Data
public class ApiResponse<T extends ApiResponseItem> {
    private Response<T> response;
    // Getters and Setters
}

