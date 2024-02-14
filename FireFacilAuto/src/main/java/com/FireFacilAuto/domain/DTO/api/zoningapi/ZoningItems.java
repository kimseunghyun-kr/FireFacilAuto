package com.FireFacilAuto.domain.DTO.api.zoningapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = ZoningItemsDeserializer.class)
@NoArgsConstructor
public class ZoningItems {

    public List<ZoningResponseItem> item;

    public ZoningItems(List<ZoningResponseItem> item) {
        this.item = item;
    }
}
