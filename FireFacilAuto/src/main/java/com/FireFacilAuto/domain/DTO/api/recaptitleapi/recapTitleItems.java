package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = recapTitleItemsDeserializer.class)
@NoArgsConstructor
public class recapTitleItems {

    public List<recapTitleResponseItem> item;

    public recapTitleItems(List<recapTitleResponseItem> item) {
        this.item = item;
    }
}
