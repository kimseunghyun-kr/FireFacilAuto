package com.FireFacilAuto.domain.DTO.api.watercatchmentfiltrationapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class WaterCatchmentFiltrationResponse {
    private ResponseHeader header;
    private WaterCatchmentFiltrationResponseBody body;

}
