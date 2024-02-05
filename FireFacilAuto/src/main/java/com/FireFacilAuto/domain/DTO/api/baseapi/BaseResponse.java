package com.FireFacilAuto.domain.DTO.api.baseapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class BaseResponse {
    private ResponseHeader header;
    private BaseResponseBody body;

}
