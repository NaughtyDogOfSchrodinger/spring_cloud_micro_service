package com.jianghu.mscore.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.20
 */
public class JsonUtils {
    /**
     * The constant OBJECT_MAPPER.
     */
    public static final ObjectMapper OBJECT_MAPPER = createObjectMapper();


    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * 对象转json
     *
     * @param o the o
     * @return the string
     * @since 2019.06.20
     */
    public static String object2Json(Object o) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;

        try {
            gen = (new JsonFactory()).createGenerator(sw);
            OBJECT_MAPPER.writeValue(gen, o);
        } catch (IOException var11) {
            throw new RuntimeException("不能序列化对象为Json", var11);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException var10) {
                    throw new RuntimeException("不能序列化对象为Json", var10);
                }
            }

        }

        return sw.toString();
    }

    /**
     * 对象转map
     *
     * @param o the o
     * @return the map
     * @since 2019.06.20
     */
    public static Map<String, Object> object2Map(Object o) {
        return (Map)OBJECT_MAPPER.convertValue(o, Map.class);
    }

    /**
     * json转对象
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t
     * @since 2019.06.20
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException var3) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, var3);
        }
    }

    /**
     * json转list
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the list
     * @throws IOException the io exception
     * @since 2019.06.20
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return (List)OBJECT_MAPPER.readValue(json, type);
    }

    /**
     * json转数组
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t [ ]
     * @throws IOException the io exception
     * @since 2019.06.20
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
        return (T[]) OBJECT_MAPPER.readValue(json, clazz);
    }

    /**
     * JsonNode转对象
     *
     * @param <T>      the type parameter
     * @param jsonNode the json node
     * @param clazz    the clazz
     * @return the t
     * @since 2019.06.20
     */
    public static <T> T node2Object(JsonNode jsonNode, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode, clazz);
        } catch (JsonProcessingException var3) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + jsonNode.toString(), var3);
        }
    }

    /**
     * 对象转JsonNode
     *
     * @param o the o
     * @return the json node
     * @since 2019.06.20
     */
    public static JsonNode object2Node(Object o) {
        try {
            return (JsonNode)(o == null ? OBJECT_MAPPER.createObjectNode() : (JsonNode)OBJECT_MAPPER.convertValue(o, JsonNode.class));
        } catch (Exception var2) {
            throw new RuntimeException("不能序列化对象为Json", var2);
        }
    }

    /**
     * 验证json
     *
     * @param input the input
     * @return the boolean
     * @since 2019.06.20
     */
    public static boolean validate(String input) {
        input = input.trim();
        boolean ret = (new JsonValidator()).validate(input);
        return ret;
    }
}

