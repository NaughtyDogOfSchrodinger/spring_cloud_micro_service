package com.jianghu.mscore.cache.handler.base;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisOperationsInterface<K> {

    Boolean hasKey(K var1);

    Boolean expire(K var1, long var2, TimeUnit var4);

    Boolean expireAt(K var1, Date var2);

    Long getExpire(K var1, TimeUnit var2);

    Long getExpire(K var1);

    void delete(K var1);

    void delete(Collection<K> var1);

    Set<K> keys(K var1);
}
