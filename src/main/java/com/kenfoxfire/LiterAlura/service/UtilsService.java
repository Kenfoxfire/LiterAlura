package com.kenfoxfire.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class UtilsService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T transformData(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json,targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
