package com.FireFacilAuto.domain.DTO.api.baseapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = BaseItemsDeserializer.class)
@NoArgsConstructor
public class BaseItems {

    public List<BaseResponseItem> item;

    public BaseItems(List<BaseResponseItem> item) {
        this.item = item;
    }
}
