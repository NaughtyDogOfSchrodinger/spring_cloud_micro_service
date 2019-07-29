package com.jianghu.mscore.cache.handler.base;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface RedisValueOperationsInterface<K, V> extends RedisOperationsInterface<K> {
    void set(K var1, V var2);

    void set(K var1, V var2, long var3, TimeUnit var5);

    Boolean setIfAbsent(K var1, V var2);

    void multiSet(Map<? extends K, ? extends V> var1);

    Boolean multiSetIfAbsent(Map<? extends K, ? extends V> var1);

    V get(Object var1);

    V getAndSet(K var1, V var2);

    List<V> multiGet(Collection<K> var1);

    Long increment(K var1, long var2);

    Double increment(K var1, double var2);

    Integer append(K var1, String var2);

    String get(K var1, long var2, long var4);

    void set(K var1, V var2, long var3);

    Long size(K var1);

    Boolean setBit(K var1, long var2, boolean var4);

    Boolean getBit(K var1, long var2);
}

