package com.FireFacilAuto.domain.DTO.api;

import lombok.Data;

@Data
public class Response < T extends ApiResponseItem> {
    private ResponseHeader header;
    private ResponseBody<T> body;

}
