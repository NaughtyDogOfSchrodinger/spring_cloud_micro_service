package com.jianghu.mscore.cache.handler;

import com.jianghu.mscore.cache.handler.base.RedisValueOperationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisValueHandler extends AbstractRedisHandler<String> implements RedisValueOperationsInterface<String, Object> {

    @Autowired
    public RedisValueHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        this.redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Boolean setIfAbsent(String key, Object value) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public void multiSet(Map<? extends String, ?> map) {
        this.redisTemplate.opsForValue().multiSet(map);
    }

    public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
        return this.redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    public Object get(Object key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, Object value) {
        return this.redisTemplate.opsForValue().getAndSet(key, value);
    }

    public List<Object> multiGet(Collection<String> keys) {
        return this.redisTemplate.opsForValue().multiGet(keys);
    }

    public Long increment(String key, long delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    public Double increment(String key, double delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    public Integer append(String key, String value) {
        return this.redisTemplate.opsForValue().append(key, value);
    }

    public String get(String key, long start, long end) {
        return this.redisTemplate.opsForValue().get(key, start, end);
    }

    public void set(String key, Object value, long offset) {
        this.redisTemplate.opsForValue().set(key, value, offset);
    }

    public Long size(String key) {
        return this.redisTemplate.opsForValue().size(key);
    }

    public Boolean setBit(String key, long offset, boolean value) {
        return this.redisTemplate.opsForValue().setBit(key, offset, value);
    }

    public Boolean getBit(String key, long offset) {
        return this.redisTemplate.opsForValue().getBit(key, offset);
    }
}

