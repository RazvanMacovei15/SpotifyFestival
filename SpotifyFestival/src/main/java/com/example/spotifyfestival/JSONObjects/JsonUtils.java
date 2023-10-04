package com.example.spotifyfestival.JSONObjects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T deserializeJson(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
