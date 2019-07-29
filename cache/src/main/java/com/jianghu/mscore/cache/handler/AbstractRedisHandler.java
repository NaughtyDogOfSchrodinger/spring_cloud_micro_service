package com.jianghu.mscore.cache.handler;

import com.jianghu.mscore.cache.handler.base.RedisOperationsInterface;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class AbstractRedisHandler<K> implements RedisOperationsInterface<K> {

    public RedisTemplate<K, Object> redisTemplate;

    public AbstractRedisHandler() {
    }

    public Boolean hasKey(K key) {
        return this.redisTemplate.hasKey(key);
    }

    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return this.redisTemplate.expire(key, timeout, unit);
    }

    public Boolean expireAt(K key, Date date) {
        return this.redisTemplate.expireAt(key, date);
    }

    public Long getExpire(K key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public Long getExpire(K key) {
        return this.redisTemplate.getExpire(key);
    }

    public void delete(K key) {
        this.redisTemplate.delete(key);
    }

    public void delete(Collection<K> keys) {
        this.redisTemplate.delete(keys);
    }

    public Set<K> keys(K pattern) {
        return this.redisTemplate.keys(pattern);
    }
}

