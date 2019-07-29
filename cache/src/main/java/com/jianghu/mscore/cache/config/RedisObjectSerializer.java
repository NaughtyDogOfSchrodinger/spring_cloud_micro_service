package com.jianghu.mscore.cache.config;

import com.jianghu.mscore.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

class RedisObjectSerializer implements RedisSerializer<Object> {
    private Logger logger = LoggerFactory.getLogger(RedisObjectSerializer.class);
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();
    private static final byte[] EMPTY_ARRAY = new byte[0];

    RedisObjectSerializer() {
    }

    public Object deserialize(byte[] bytes) {
        if (this.isEmpty(bytes)) {
            return null;
        } else {
            try {
                return this.deserializer.convert(bytes);
            } catch (Exception var3) {
                this.logger.error("RedisObjectSerializer deserialize bytes:{},size:{}", bytes, bytes.length);
                throw new SerializationException("Cannot deserialize", var3);
            }
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        } else {
            try {
                byte[] bytes = (byte[])this.serializer.convert(object);
                this.logger.debug("RedisObjectSerializer serialize byte[]:{},size:{}", bytes, bytes.length);
                return bytes;
            } catch (Exception var3) {
                this.logger.error("RedisObjectSerializer serialize object:{}", ObjectUtil.toString(object));
                return EMPTY_ARRAY;
            }
        }
    }

    private boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }
}

