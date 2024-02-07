package com.FireFacilAuto.domain.DTO.api.zoningapi;

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

public class ZoningItemsDeserializer extends StdDeserializer<ZoningItems> {

    public ZoningItemsDeserializer() {
        super(ZoningItems.class);
    }

    @Override
    public ZoningItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new ZoningItems(Collections.emptyList());
        } else {
            List<ZoningResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, ZoningResponseItem[].class)).toList();
            return new ZoningItems(itemList);
        }
    }
}