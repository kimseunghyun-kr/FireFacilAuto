package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
@JsonDeserialize(using = ItemsDeserializer.class)
public class Items {

    private List<ApiResponseItem> item;


}
