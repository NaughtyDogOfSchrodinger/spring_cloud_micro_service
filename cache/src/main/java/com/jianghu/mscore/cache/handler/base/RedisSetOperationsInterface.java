package com.jianghu.mscore.cache.handler.base;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RedisSetOperationsInterface<K, V> extends RedisOperationsInterface<K> {
    Long add(K var1, V... var2);

    Long remove(K var1, Object... var2);

    V pop(K var1);

    Boolean move(K var1, V var2, K var3);

    Long size(K var1);

    Boolean isMember(K var1, Object var2);

    Set<V> intersect(K var1, K var2);

    Set<V> intersect(K var1, Collection<K> var2);

    Long intersectAndStore(K var1, K var2, K var3);

    Long intersectAndStore(K var1, Collection<K> var2, K var3);

    Set<V> union(K var1, K var2);

    Set<V> union(K var1, Collection<K> var2);

    Long unionAndStore(K var1, K var2, K var3);

    Long unionAndStore(K var1, Collection<K> var2, K var3);

    Set<V> difference(K var1, K var2);

    Set<V> difference(K var1, Collection<K> var2);

    Long differenceAndStore(K var1, K var2, K var3);

    Long differenceAndStore(K var1, Collection<K> var2, K var3);

    Set<V> members(K var1);

    V randomMember(K var1);

    Set<V> distinctRandomMembers(K var1, long var2);

    List<V> randomMembers(K var1, long var2);
}

