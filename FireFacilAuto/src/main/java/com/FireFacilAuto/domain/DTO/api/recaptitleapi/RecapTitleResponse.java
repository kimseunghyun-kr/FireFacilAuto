package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecapTitleResponse {
    private ResponseHeader header;
    @JsonProperty("body")
    private RecapTitleResponseBody body;

}
