package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class RecapTitleResponse {
    private ResponseHeader header;
    private RecapTitleResponseBody body;

}
