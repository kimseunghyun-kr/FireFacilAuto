package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import java.util.List;

@Data
@JsonDeserialize(using = ItemsDeserializer.class)
public  class Items<T extends ApiResponseItem> {

    private List<T> item;

    public Items(List<T> objects) {
        this.item = objects;
    }
}
