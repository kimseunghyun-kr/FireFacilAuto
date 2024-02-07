package com.FireFacilAuto.domain.DTO.api.housingpriceapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class HousingPriceResponse {
    private ResponseHeader header;
    private HousingPriceResponseBody body;

}
