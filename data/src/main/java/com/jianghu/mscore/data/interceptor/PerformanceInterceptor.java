package com.jianghu.mscore.data.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.jianghu.mscore.context.config.ApplicationContextHolder;
import com.jianghu.mscore.util.ArrayUtil;
import com.jianghu.mscore.util.ObjectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
)})
public class PerformanceInterceptor implements Interceptor {
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);

    public PerformanceInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        if (ObjectUtil.isNullObj(ApplicationContextHolder.context)) {
            return invocation.proceed();
        } else {
            String[] actProfile = ApplicationContextHolder.context.getEnvironment().getActiveProfiles();
            if (actProfile.length > 0 && !ArrayUtil.getUniqueSqlString(actProfile).contains("dev")) {
                return invocation.proceed();
            } else {
                MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
                Object parameterObject = null;
                if (invocation.getArgs().length > 1) {
                    parameterObject = invocation.getArgs()[1];
                }

                String statementId = mappedStatement.getId();
                BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
                Configuration configuration = mappedStatement.getConfiguration();
                this.getSql(boundSql, parameterObject, configuration);
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                Object result = invocation.proceed();
                stopWatch.split();
                this.logger.info("==> Times: {}ms, {}", stopWatch.getSplitTime(), statementId);
                return result;
            }
        }
    }

    public Object plugin(Object target) {
        return target instanceof Executor ? Plugin.wrap(target, this) : target;
    }

    public void setProperties(Properties properties) {
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (CollectionUtils.isEmpty(parameterMappings)) {
            return sql;
        } else {
            Iterator var6 = parameterMappings.iterator();

            while(var6.hasNext()) {
                ParameterMapping parameterMapping = (ParameterMapping)var6.next();
                if (!ParameterMode.OUT.equals(parameterMapping.getMode())) {
                    Object value = this.fillInValue(boundSql, parameterMapping, parameterObject, configuration);
                    sql = this.replacePlaceholder(sql, value);
                }
            }

            return sql;
        }
    }

    private Object fillInValue(BoundSql boundSql, ParameterMapping parameterMapping, Object parameterObject, Configuration configuration) {
        String propertyName = parameterMapping.getProperty();
        if (boundSql.hasAdditionalParameter(propertyName)) {
            return boundSql.getAdditionalParameter(propertyName);
        } else if (parameterObject == null) {
            return null;
        } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
            return parameterObject;
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            return metaObject.getValue(propertyName);
        }
    }

    private String replacePlaceholder(String sql, Object propertyValue) {
        StringBuilder result = new StringBuilder();
        if (ObjectUtil.isNullObj(propertyValue)) {
            return "null";
        } else {
            if (propertyValue instanceof String) {
                result.append("'").append(propertyValue).append("'");
            } else if (propertyValue instanceof Date) {
                result.append("'").append(this.DATE_FORMAT.format(propertyValue)).append("'");
            } else {
                result.append(propertyValue.toString());
            }

            return sql.replaceFirst("\\?", result.toString());
        }
    }
}

