package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Data
@JsonDeserialize(using = ItemsDeserializer.class)
public class Items {

    private List<ApiResponseItem> item;

    public Items() {
    }


    public Items(List<ApiResponseItem> objects) {
        this.item = objects;
    }
}
