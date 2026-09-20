package gnoolson.saturday.app.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class JSON {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /*
    *
    *
    * */
    public static <T>T parseObject(String json, Class<?> clazz) {
        try {
            return (T) JSON_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String json, Class<?> clazz){
        try {
            CollectionType listType = JSON_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, clazz);

            return JSON_MAPPER.readValue(json, listType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONString(Object object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convert(JsonNode jsonNode, TypeReference<?> typeReference) {
        return (T) JSON_MAPPER.convertValue(jsonNode, typeReference);
    }

    public static ObjectNode createObjectNode(){
        return JSON_MAPPER.createObjectNode();
    }

}
