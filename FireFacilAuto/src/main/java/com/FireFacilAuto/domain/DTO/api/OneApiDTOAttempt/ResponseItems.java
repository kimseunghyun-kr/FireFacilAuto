package com.FireFacilAuto.domain.DTO.api.OneApiDTOAttempt;

import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = ItemsDeserializer.class)
@NoArgsConstructor
public class ResponseItems {

    public List<? extends ResponseClass> item;

    public ResponseItems(List<? extends ResponseClass> item) {
        this.item = item;
    }

}
