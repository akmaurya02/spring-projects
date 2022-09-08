package com.spring.customapp.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperService {

    private static ObjectMapper objectMapper;


    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public <T extends Object> T jsonToObjectMapper(String data, Class<T> className) {
        T t = null;
        try {
            t = objectMapper.readValue(data, className);
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE CONVERTING JSON TO OBJECT:: " + e + ", DATA :: " + data + ", CLASSNAME:: " + className);
        }
        return t;
    }


    public <T> String objectToJsonMapper(T data) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            System.out.println("ERROR OCCURRED WHILE CONVERTING OBJECT TO JSON:: " + e + ", DATA :: " + data);
        }
        return jsonString;
    }


}
