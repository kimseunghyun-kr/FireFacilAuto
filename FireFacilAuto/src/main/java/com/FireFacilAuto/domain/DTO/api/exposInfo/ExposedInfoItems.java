package com.FireFacilAuto.domain.DTO.api.exposInfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = ExposedInfoItemsDeserializer.class)
@NoArgsConstructor
public class ExposedInfoItems {

    public List<ExposedInfoResponseItem> item;

    public ExposedInfoItems(List<ExposedInfoResponseItem> item) {
        this.item = item;
    }
}
