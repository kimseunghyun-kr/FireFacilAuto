package com.FireFacilAuto.domain.DTO.api.floorapi;

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

public class FloorItemsDeserializer extends StdDeserializer<FloorItems> {

    public FloorItemsDeserializer() {
        super(FloorItems.class);
    }

    @Override
    public FloorItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new FloorItems(Collections.emptyList());
        } else {
            List<FloorResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, FloorResponseItem[].class)).toList();
            return new FloorItems(itemList);
        }
    }
}