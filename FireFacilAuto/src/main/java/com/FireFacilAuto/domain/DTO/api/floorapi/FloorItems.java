package com.FireFacilAuto.domain.DTO.api.floorapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = FloorItemsDeserializer.class)
@NoArgsConstructor
public class FloorItems {

    public List<FloorResponseItem> item;

    public FloorItems(List<FloorResponseItem> item) {
        this.item = item;
    }
}
