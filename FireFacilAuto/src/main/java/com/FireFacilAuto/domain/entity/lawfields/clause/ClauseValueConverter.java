package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;


public class ClauseValueConverter<T> implements AttributeConverter<ClauseValueWrapper<T>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(ClauseValueWrapper<T> clauseValueWrapper) {
        try {
            return objectMapper.writeValueAsString(clauseValueWrapper);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting ClauseValueWrapper to JSON", e);
        }
    }

    @Override
    public ClauseValueWrapper<T> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to ClauseValueWrapper", e);
        }
    }
}
