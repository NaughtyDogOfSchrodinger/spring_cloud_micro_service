package com.jianghu.mscore.cache.handler;

import com.jianghu.mscore.cache.handler.base.RedisListOperationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisListHandler extends AbstractRedisHandler<String> implements RedisListOperationsInterface<String, Object> {
    @Autowired
    public RedisListHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<Object> range(String key, long start, long end) {
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    public void trim(String key, long start, long end) {
        this.redisTemplate.opsForList().trim(key, start, end);
    }

    public Long size(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    public Long leftPush(String key, Object value) {
        return this.redisTemplate.opsForList().leftPush(key, value);
    }

    public Long leftPushAll(String key, Object... values) {
        return this.redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Long leftPushAll(String key, Collection<Object> values) {
        return this.redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Long leftPushIfPresent(String key, Object value) {
        return this.redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    public Long leftPush(String key, Object pivot, Object value) {
        return this.redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    public Long rightPush(String key, Object value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    public Long rightPushAll(String key, Object... values) {
        return this.redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long rightPushAll(String key, Collection<Object> values) {
        return this.redisTemplate.opsForList().rightPushAll(key, values);
    }

    public Long rightPushIfPresent(String key, Object value) {
        return this.redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    public Long rightPush(String key, Object pivot, Object value) {
        return this.redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    public void set(String key, long index, Object value) {
        this.redisTemplate.opsForList().set(key, index, value);
    }

    public Long remove(String key, long count, Object value) {
        return this.redisTemplate.opsForList().remove(key, count, value);
    }

    public Object index(String key, long index) {
        return this.redisTemplate.opsForList().index(key, index);
    }

    public Object leftPop(String key) {
        return this.redisTemplate.opsForList().leftPop(key);
    }

    public Object leftPop(String key, long timeout, TimeUnit unit) {
        return this.redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    public Object rightPop(String key) {
        return this.redisTemplate.opsForList().rightPop(key);
    }

    public Object rightPop(String key, long timeout, TimeUnit unit) {
        return this.redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    public Object rightPopAndLeftPush(String sourceKey, String destinationKey) {
        return this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    public Object rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return this.redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }
}

