package com.textadventure.textProcessor.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.textadventure.textProcessor.model.Item;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Converter(autoApply = true)
public class JsonBConverter implements AttributeConverter<List<Item>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Item> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting List<Item> to JSON", e);
        }
    }

    @Override
    public List<Item> convertToEntityAttribute(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList(); // Return an empty list instead of null
        }
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Item.class);
            return objectMapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to List<Item>", e);
        }
    }
}