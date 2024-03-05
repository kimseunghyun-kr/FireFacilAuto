package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Converter
@Slf4j
public class ClauseListConverter implements AttributeConverter<List<Clause<?>>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Clause<?>> clauses) {
        try {
            String deserialisedJson = objectMapper.writeValueAsString(clauses);
            log.info("clause list deserialised into {}, ", deserialisedJson);
            return deserialisedJson;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting List<Clause<?>> to database column", e);
        }
    }

    @Override
    public List<Clause<?>> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting database column to List<Clause<?>>", e);
        }
    }
}

