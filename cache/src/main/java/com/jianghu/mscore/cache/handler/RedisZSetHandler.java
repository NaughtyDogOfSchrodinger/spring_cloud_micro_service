package com.jianghu.mscore.cache.handler;

import com.jianghu.mscore.cache.handler.base.RedisZSetOperationsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisZSetHandler extends AbstractRedisHandler<String> implements RedisZSetOperationsInterface<String, Object> {
    @Autowired
    public RedisZSetHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean add(String key, Object value, double score) {
        return this.redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long add(String key, Set typedTuples) {
        return this.redisTemplate.opsForZSet().add(key, typedTuples);
    }

    public Long remove(String key, Object... values) {
        return this.redisTemplate.opsForZSet().remove(key, values);
    }

    public Double incrementScore(String key, Object value, double delta) {
        return null;
    }

    public Long rank(String key, Object o) {
        return this.redisTemplate.opsForZSet().rank(key, o);
    }

    public Long reverseRank(String key, Object o) {
        return this.redisTemplate.opsForZSet().reverseRank(key, o);
    }

    public Set<Object> range(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set rangeWithScores(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    public Set<Object> rangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Set rangeByScoreWithScores(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    public Set<Object> rangeByScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Set rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<Object> reverseRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set reverseRangeWithScores(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    public Set<Object> reverseRangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public Set reverseRangeByScoreWithScores(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    public Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    public Set reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    public Long count(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().count(key, min, max);
    }

    public Long size(String key) {
        return this.redisTemplate.opsForZSet().size(key);
    }

    public Long zCard(String key) {
        return this.redisTemplate.opsForZSet().zCard(key);
    }

    public Double score(String key, Object o) {
        return this.redisTemplate.opsForZSet().score(key, o);
    }

    public Long removeRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public Long removeRangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    public Long unionAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    public Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return this.redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }
}
