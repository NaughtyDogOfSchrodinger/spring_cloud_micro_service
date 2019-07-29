package com.jianghu.mscore.cache.handler.base;

import java.util.Collection;
import java.util.Set;

public interface RedisZSetOperationsInterface<K, V> extends RedisOperationsInterface<K> {
    Boolean add(K var1, V var2, double var3);

    Long add(K var1, Set var2);

    Long remove(K var1, Object... var2);

    Double incrementScore(K var1, V var2, double var3);

    Long rank(K var1, Object var2);

    Long reverseRank(K var1, Object var2);

    Set<V> range(K var1, long var2, long var4);

    Set rangeWithScores(K var1, long var2, long var4);

    Set<V> rangeByScore(K var1, double var2, double var4);

    Set rangeByScoreWithScores(K var1, double var2, double var4);

    Set<V> rangeByScore(K var1, double var2, double var4, long var6, long var8);

    Set rangeByScoreWithScores(K var1, double var2, double var4, long var6, long var8);

    Set<V> reverseRange(K var1, long var2, long var4);

    Set reverseRangeWithScores(K var1, long var2, long var4);

    Set<V> reverseRangeByScore(K var1, double var2, double var4);

    Set reverseRangeByScoreWithScores(K var1, double var2, double var4);

    Set<V> reverseRangeByScore(K var1, double var2, double var4, long var6, long var8);

    Set reverseRangeByScoreWithScores(K var1, double var2, double var4, long var6, long var8);

    Long count(K var1, double var2, double var4);

    Long size(K var1);

    Long zCard(K var1);

    Double score(K var1, Object var2);

    Long removeRange(K var1, long var2, long var4);

    Long removeRangeByScore(K var1, double var2, double var4);

    Long unionAndStore(K var1, K var2, K var3);

    Long unionAndStore(K var1, Collection<K> var2, K var3);

    Long intersectAndStore(K var1, K var2, K var3);

    Long intersectAndStore(K var1, Collection<K> var2, K var3);
}

