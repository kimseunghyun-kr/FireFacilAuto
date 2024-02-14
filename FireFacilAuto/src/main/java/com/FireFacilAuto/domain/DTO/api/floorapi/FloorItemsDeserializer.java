package com.FireFacilAuto.domain.DTO.api.floorapi;

import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleItems;
import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleResponseItem;
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
        } else if (itemNode.isObject()){
            List<FloorResponseItem> itemList = new LinkedList<>();
            itemList.add(objectMapper.treeToValue(itemNode, FloorResponseItem.class));
            return new FloorItems(itemList);
        } else {
            List<FloorResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, FloorResponseItem[].class)).toList();
            return new FloorItems(itemList);
        }
    }
}