package com.FireFacilAuto.domain.DTO.api.exposInfo;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class ExposedInfoResponse {
    private ResponseHeader header;
    private ExposedInfoResponseBody body;

}
