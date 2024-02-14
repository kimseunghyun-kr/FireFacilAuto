package com.FireFacilAuto.domain.DTO.api.watercatchmentfiltrationapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = WaterCatchmentFiltrationItemsDeserializer.class)
@NoArgsConstructor
public class WaterCatchmentFiltrationItems {

    public List<WaterCatchmentFiltrationResponseItem> item;

    public WaterCatchmentFiltrationItems(List<WaterCatchmentFiltrationResponseItem> item) {
        this.item = item;
    }
}
