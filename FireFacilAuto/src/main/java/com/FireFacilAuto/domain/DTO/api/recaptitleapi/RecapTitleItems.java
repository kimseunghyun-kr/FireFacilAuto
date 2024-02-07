package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = RecapTitleItemsDeserializer.class)
@NoArgsConstructor
public class RecapTitleItems {

    public List<RecapTitleResponseItem> item;

    public RecapTitleItems(List<RecapTitleResponseItem> item) {
        this.item = item;
    }
}
