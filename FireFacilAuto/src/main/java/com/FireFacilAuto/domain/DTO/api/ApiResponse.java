package com.FireFacilAuto.domain.DTO.api;

import lombok.Data;


@Data
public class ApiResponse {

    private ResponseHeader header;
    private ResponseBody body;

    // Getters and Setters
}

