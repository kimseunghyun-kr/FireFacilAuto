package com.FireFacilAuto.domain.DTO.api.exposInfo;

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

public class ExposedInfoItemsDeserializer extends StdDeserializer<ExposedInfoItems> {

    public ExposedInfoItemsDeserializer() {
        super(ExposedInfoItems.class);
    }

    @Override
    public ExposedInfoItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new ExposedInfoItems(Collections.emptyList());
        } else {
            List<ExposedInfoResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, ExposedInfoResponseItem[].class)).toList();
            return new ExposedInfoItems(itemList);
        }
    }
}