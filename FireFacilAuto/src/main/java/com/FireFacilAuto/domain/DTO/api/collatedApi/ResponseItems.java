package com.FireFacilAuto.domain.DTO.api.collatedApi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonDeserialize(using = ItemsDeserializer.class)
@NoArgsConstructor

public class ResponseItems {


}
