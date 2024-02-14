package com.FireFacilAuto.domain.DTO.api.floorapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class FloorResponse {
    private ResponseHeader header;
    private FloorResponseBody body;

}
