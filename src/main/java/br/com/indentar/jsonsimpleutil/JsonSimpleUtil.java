/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.indentar.jsonsimpleutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Marlucio
 */
public class JsonSimpleUtil {

    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        return mapper;
    }

    public static <T> T toObject(String json, Class clazz) throws JsonProcessingException {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            return (T) getMapper()
                    .readValue(json, clazz);
        }
    }

    public static <T> T toCollection(String json, Class objectClazz, Class collectionClazz) throws JsonProcessingException {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            ObjectMapper mapper = getMapper();
            return (T) mapper.readValue(json, mapper.getTypeFactory()
                    .constructCollectionType(collectionClazz, objectClazz));
        }
    }

    /**
     *
     * @param <T> List<T>
     * @param json
     * @param objectClazz
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T toCollection(String json, Class objectClazz) throws JsonProcessingException {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            ObjectMapper mapper = getMapper();
            return (T) mapper.readValue(json, mapper.getTypeFactory()
                    .constructCollectionType(List.class, objectClazz));
        }
    }

    public static ObjectNode createObjectNode() {
        ObjectNode createObjectNode = getMapper().createObjectNode();
        return createObjectNode;
    }

    public static <T> T jsonToObj(String json, Class<?> bean) throws JsonProcessingException {
        ObjectMapper mapper = getMapper();
        return (T) mapper.readValue(json, bean);
    }

    public static JsonNode jsonToJsonNode(String json) throws JsonProcessingException {
        return getMapper().readTree(json);
    }

    public static String toJson(Object value) throws JsonProcessingException {
        return getMapper().writeValueAsString(value);
    }

}
