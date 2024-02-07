package com.FireFacilAuto.domain.DTO.api.titleresponseapi;

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

public class TitleItemsDeserializer extends StdDeserializer<TitleItems> {

    public TitleItemsDeserializer() {
        super(TitleItems.class);
    }

    @Override
    public TitleItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new TitleItems(Collections.emptyList());
        } else {
            List<TitleResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, TitleResponseItem[].class)).toList();
            return new TitleItems(itemList);
        }
    }
}