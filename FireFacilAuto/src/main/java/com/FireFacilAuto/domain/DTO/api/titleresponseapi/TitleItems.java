package com.FireFacilAuto.domain.DTO.api.titleresponseapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = TitleItemsDeserializer.class)
@NoArgsConstructor
public class TitleItems {

    public List<TitleResponseItem> item;

    public TitleItems(List<TitleResponseItem> item) {
        this.item = item;
    }
}
