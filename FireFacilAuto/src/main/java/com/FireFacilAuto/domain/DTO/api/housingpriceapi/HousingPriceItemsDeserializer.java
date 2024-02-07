package com.FireFacilAuto.domain.DTO.api.housingpriceapi;

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

public class HousingPriceItemsDeserializer extends StdDeserializer<HousingPriceItems> {

    public HousingPriceItemsDeserializer() {
        super(HousingPriceItems.class);
    }

    @Override
    public HousingPriceItems deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = p.getCodec().readTree(p);
        JsonNode itemNode = node.findValue("item");

        if (isNull(itemNode)) {
            return new HousingPriceItems(Collections.emptyList());
        } else {
            List<HousingPriceResponseItem> itemList = Arrays.stream(objectMapper.treeToValue(itemNode, HousingPriceResponseItem[].class)).toList();
            return new HousingPriceItems(itemList);
        }
    }
}