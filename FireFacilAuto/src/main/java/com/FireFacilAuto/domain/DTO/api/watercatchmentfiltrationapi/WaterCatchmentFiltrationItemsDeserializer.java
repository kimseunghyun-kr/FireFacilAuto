package com.FireFacilAuto.domain.DTO.api.watercatchmentfiltrationapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class WaterCatchmentFiltrationItemsDeserializer extends StdDeserializer<WaterCatchmentFiltrationItems> {

    public WaterCatchmentFiltrationItemsDeserializer() {
        super(WaterCatchmentFiltrationItems.class);
    }

    @Override
    public WaterCatchmentFiltrationItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new WaterCatchmentFiltrationItems(Collections.emptyList());
        } else {
            List<WaterCatchmentFiltrationResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, WaterCatchmentFiltrationResponseItem[].class)).toList();
            return new WaterCatchmentFiltrationItems(itemList);
        }
    }
}