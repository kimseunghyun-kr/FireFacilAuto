package com.FireFacilAuto.domain.DTO.api.titleresponseapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class TitleResponse {
    private ResponseHeader header;
    private TitleResponseBody body;

}
