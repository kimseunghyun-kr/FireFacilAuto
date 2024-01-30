package com.FireFacilAuto.domain.DTO.api;

import lombok.Data;

@Data
public class Response {
    private ResponseHeader header;
    private ResponseBody body;

}
