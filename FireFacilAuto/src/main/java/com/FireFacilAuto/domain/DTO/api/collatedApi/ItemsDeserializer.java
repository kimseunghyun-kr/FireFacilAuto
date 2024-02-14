package com.FireFacilAuto.domain.DTO.api.collatedApi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ItemsDeserializer extends StdDeserializer<List<? extends ResponseClass>> implements ContextualDeserializer {

    public ItemsDeserializer() {
        this(null);
    }

    protected ItemsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<? extends ResponseClass> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        Class<? extends ResponseClass> classType = (Class<? extends ResponseClass>) this._valueClass;

        ObjectMapper objectMapper = new ObjectMapper();
        // Assuming your JSON structure contains an array of items
        List<? extends ResponseClass> itemList = Arrays.asList(objectMapper.treeToValue(jsonNode, classType));

        return itemList;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return new ItemsDeserializer(property.getType().getRawClass());
    }

}
