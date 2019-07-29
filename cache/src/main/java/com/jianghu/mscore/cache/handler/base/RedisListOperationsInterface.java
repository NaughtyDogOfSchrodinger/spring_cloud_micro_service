package com.jianghu.mscore.cache.handler.base;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisListOperationsInterface<K, V> extends RedisOperationsInterface<K> {
    List<V> range(K var1, long var2, long var4);

    void trim(K var1, long var2, long var4);

    Long size(K var1);

    Long leftPush(K var1, V var2);

    Long leftPushAll(K var1, V... var2);

    Long leftPushAll(K var1, Collection<V> var2);

    Long leftPushIfPresent(K var1, V var2);

    Long leftPush(K var1, V var2, V var3);

    Long rightPush(K var1, V var2);

    Long rightPushAll(K var1, V... var2);

    Long rightPushAll(K var1, Collection<V> var2);

    Long rightPushIfPresent(K var1, V var2);

    Long rightPush(K var1, V var2, V var3);

    void set(K var1, long var2, V var4);

    Long remove(K var1, long var2, Object var4);

    V index(K var1, long var2);

    V leftPop(K var1);

    V leftPop(K var1, long var2, TimeUnit var4);

    V rightPop(K var1);

    V rightPop(K var1, long var2, TimeUnit var4);

    V rightPopAndLeftPush(K var1, K var2);

    V rightPopAndLeftPush(K var1, K var2, long var3, TimeUnit var5);
}

