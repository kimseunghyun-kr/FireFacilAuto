package com.FireFacilAuto.domain.DTO.api.recaptitleapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class RecapTitleItemsDeserializer extends StdDeserializer<RecapTitleItems> {

    public RecapTitleItemsDeserializer() {
        super(RecapTitleItems.class);
    }

    @Override
    public RecapTitleItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new RecapTitleItems(Collections.emptyList());
        } else if (itemNode.isObject()){
            List<RecapTitleResponseItem> itemList = new LinkedList<>();
            itemList.add(objectMapper.treeToValue(itemNode, RecapTitleResponseItem.class));
            return new RecapTitleItems(itemList);
        } else {
            List<RecapTitleResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, RecapTitleResponseItem[].class)).toList();
            return new RecapTitleItems(itemList);
        }
    }
}