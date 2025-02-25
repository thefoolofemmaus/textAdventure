package com.textadventure.textProcessor.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Converter(autoApply = true)
public class JsonBConverter implements AttributeConverter<List<Map<String, Object>>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Map<String, Object>> attribute) {
        try {
            return (attribute == null) ? "{}" : objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return "{}"; // Return default empty JSON
        }
    }

    @Override
    public List<Map<String, Object>> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>(); // Return null if invalid JSON
        }
    }
}