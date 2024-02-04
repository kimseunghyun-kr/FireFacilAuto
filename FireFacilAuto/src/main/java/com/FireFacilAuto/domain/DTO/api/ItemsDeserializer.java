package com.FireFacilAuto.domain.DTO.api;

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

public class ItemsDeserializer<T extends ApiResponseItem> extends StdDeserializer<Items<T>> {

    private final Class<T> itemType;

    public ItemsDeserializer(Class<T> itemType) {
        super(Items.class);
        this.itemType = itemType;
    }

    @Override
    public Items<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new Items<>(Collections.emptyList());
        } else {
            ObjectMapper objectMapper = (ObjectMapper) p.getCodec();
            List<T> itemList = Arrays.asList(objectMapper.treeToValue(itemNode, itemType));
            return new Items<>(itemList);
        }
    }
}
