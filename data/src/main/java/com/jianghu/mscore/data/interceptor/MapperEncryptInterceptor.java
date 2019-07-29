package com.jianghu.mscore.data.interceptor;

import java.util.Properties;

import com.jianghu.mscore.data.util.ParamEncryptHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class MapperEncryptInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(MapperEncryptInterceptor.class);

    public MapperEncryptInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getArgs().length > 1) {
            Object object = invocation.getArgs()[1];
            ParamEncryptHelper.encrypt(object);
        }

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}

