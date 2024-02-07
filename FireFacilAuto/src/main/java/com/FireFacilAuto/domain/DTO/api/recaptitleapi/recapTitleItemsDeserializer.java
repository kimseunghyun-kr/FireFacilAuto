package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

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

public class recapTitleItemsDeserializer extends StdDeserializer<recapTitleItems> {

    public recapTitleItemsDeserializer() {
        super(recapTitleItems.class);
    }

    @Override
    public recapTitleItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new recapTitleItems(Collections.emptyList());
        } else {
            List<recapTitleResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, recapTitleResponseItem[].class)).toList();
            return new recapTitleItems(itemList);
        }
    }
}