package com.jianghu.mscore.cache.handler;

import com.jianghu.mscore.cache.handler.base.RedisSetOperationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class RedisSetHandler extends AbstractRedisHandler<String> implements RedisSetOperationsInterface<String, Object> {
    @Autowired
    public RedisSetHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long add(String key, Object... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Long remove(String key, Object... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    public Object pop(String key) {
        return this.redisTemplate.opsForSet().pop(key);
    }

    public Boolean move(String key, Object value, String destKey) {
        return this.redisTemplate.opsForSet().move(key, value, destKey);
    }

    public Long size(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    public Boolean isMember(String key, Object o) {
        return this.redisTemplate.opsForSet().isMember(key, o);
    }

    public Set<Object> intersect(String key, String otherKey) {
        return this.redisTemplate.opsForSet().intersect(key, otherKey);
    }

    public Set<Object> intersect(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    public Set<Object> union(String key, String otherKey) {
        return this.redisTemplate.opsForSet().union(key, otherKey);
    }

    public Set<Object> union(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Long unionAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    public Set<Object> difference(String key, String otherKey) {
        return this.redisTemplate.opsForSet().difference(key, otherKey);
    }

    public Set<Object> difference(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public Long differenceAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    public Long differenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    public Set<Object> members(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public Object randomMember(String key) {
        return this.redisTemplate.opsForSet().randomMember(key);
    }

    public Set<Object> distinctRandomMembers(String key, long count) {
        return this.redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    public List<Object> randomMembers(String key, long count) {
        return this.redisTemplate.opsForSet().randomMembers(key, count);
    }
}

