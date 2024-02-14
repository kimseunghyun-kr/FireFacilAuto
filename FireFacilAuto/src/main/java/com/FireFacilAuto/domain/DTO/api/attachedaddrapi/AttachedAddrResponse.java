package com.FireFacilAuto.domain.DTO.api.attachedaddrapi;

import com.FireFacilAuto.domain.DTO.api.ResponseHeader;
import lombok.Data;

@Data
public class AttachedAddrResponse {
    private ResponseHeader header;
    private AttachedAddrResponseBody body;

}
