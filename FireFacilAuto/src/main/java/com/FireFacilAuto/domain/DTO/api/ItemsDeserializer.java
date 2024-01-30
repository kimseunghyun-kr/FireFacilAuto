package com.FireFacilAuto.domain.DTO.api;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class ItemsDeserializer extends StdDeserializer<Items> {
    public ItemsDeserializer() {
        this(null);
    }
    protected ItemsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Items deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.isTextual() && node.textValue().isEmpty()) {
            return new Items(); // Create an empty ItemBasket object
        } else {
            return new ObjectMapper().convertValue(node, Items.class);
        }
    }
}
