package com.FireFacilAuto.domain.DTO.api.housingpriceapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = HousingPriceItemsDeserializer.class)
@NoArgsConstructor
public class HousingPriceItems {

    public List<HousingPriceResponseItem> item;

    public HousingPriceItems(List<HousingPriceResponseItem> item) {
        this.item = item;
    }
}
