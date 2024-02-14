package com.FireFacilAuto.domain.DTO.api.exposInfo;

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
        } else if (itemNode.isObject()){
            List<ExposedInfoResponseItem> itemList = new LinkedList<>();
            itemList.add(objectMapper.treeToValue(itemNode, ExposedInfoResponseItem.class));
            return new ExposedInfoItems(itemList);
        } else {
            List<ExposedInfoResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, ExposedInfoResponseItem[].class)).toList();
            return new ExposedInfoItems(itemList);
        }
    }
}