package com.jianghu.mscore.data.interceptor;

import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.jianghu.mscore.data.util.ParamEncryptHelper;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class}
)})
public class MapperDecryptInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(MapperDecryptInterceptor.class);

    public MapperDecryptInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> objects = (List)invocation.proceed();
        ParamEncryptHelper.decrypt(objects);
        return objects;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
