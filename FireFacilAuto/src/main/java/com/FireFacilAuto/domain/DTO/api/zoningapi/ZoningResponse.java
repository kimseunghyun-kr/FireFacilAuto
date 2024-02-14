package com.FireFacilAuto.domain.DTO.api.zoningapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class ZoningResponse {
    private ResponseHeader header;
    private ZoningResponseBody body;

}
