package com.jianghu.mscore.data.util;

import com.google.common.collect.Maps;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlHelper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public SqlHelper() {
    }

    public String getMapperSql(Object mapper, String methodName, Object... args) {
        MetaObject metaObject = SystemMetaObject.forObject(mapper);
        Class mapperInterface = (Class)metaObject.getValue("h.mapperInterface");
        String fullMethodName = mapperInterface.getCanonicalName() + "." + methodName;
        return ArrayUtils.isEmpty(args) ? this.getNamespaceSql(fullMethodName, (Object)null) : this.getMapperSql(mapperInterface, methodName, args);
    }

    public String getMapperSql(String fullMapperMethodName, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return this.getNamespaceSql(fullMapperMethodName, (Object)null);
        } else {
            String methodName = fullMapperMethodName.substring(fullMapperMethodName.lastIndexOf(46) + 1);
            Class mapperInterface = null;

            try {
                mapperInterface = Class.forName(fullMapperMethodName.substring(0, fullMapperMethodName.lastIndexOf(46)));
                return this.getMapperSql(mapperInterface, methodName, args);
            } catch (ClassNotFoundException var6) {
                throw new IllegalArgumentException("参数" + fullMapperMethodName + "无效！");
            }
        }
    }

    public String getMapperSql(Class mapperInterface, String methodName, Object... args) {
        String fullMapperMethodName = mapperInterface.getCanonicalName() + "." + methodName;
        if (ArrayUtils.isEmpty(args)) {
            return this.getNamespaceSql(fullMapperMethodName, (Object)null);
        } else {
            Method method = this.getDeclaredMethods(mapperInterface, methodName);
            Map params = Maps.newHashMap();
            Class<?>[] argTypes = method.getParameterTypes();

            for(int i = 0; i < argTypes.length; ++i) {
                if (!RowBounds.class.isAssignableFrom(argTypes[i]) && !ResultHandler.class.isAssignableFrom(argTypes[i])) {
                    String paramName = "param" + String.valueOf(params.size() + 1);
                    paramName = this.getParamNameFromAnnotation(method, i, paramName);
                    params.put(paramName, i >= args.length ? null : args[i]);
                }
            }

            if (args.length == 1) {
                Object argsParams = this.wrapCollection(args[0]);
                if (argsParams instanceof Map) {
                    params.putAll((Map)argsParams);
                }
            }

            return this.getNamespaceSql(fullMapperMethodName, params);
        }
    }

    public String getNamespaceSql(String namespace) {
        return this.getNamespaceSql(namespace, (Object)null);
    }

    public String getNamespaceSql(String namespace, Object params) {
        params = this.wrapCollection(params);
        Configuration configuration = this.sqlSessionFactory.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
        BoundSql boundSql = mappedStatement.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (CollectionUtils.isEmpty(parameterMappings)) {
            return "";
        } else {
            String sql = boundSql.getSql();
            parameterMappings = (List)parameterMappings.stream().filter((parameterMappingx) -> {
                return ParameterMode.OUT != parameterMappingx.getMode();
            }).collect(Collectors.toList());

            ParameterMapping parameterMapping;
            Object value;
            JdbcType jdbcType;
            for(Iterator var8 = parameterMappings.iterator(); var8.hasNext(); sql = this.replaceParameter(sql, value, jdbcType, parameterMapping.getJavaType())) {
                parameterMapping = (ParameterMapping)var8.next();
                value = this.fillValue(namespace, parameterMapping, params, configuration);
                jdbcType = parameterMapping.getJdbcType();
                if (value == null && jdbcType == null) {
                    jdbcType = configuration.getJdbcTypeForNull();
                }
            }

            return sql;
        }
    }

    private Object fillValue(String namespace, ParameterMapping parameterMapping, Object params, Configuration configuration) {
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
        BoundSql boundSql = mappedStatement.getBoundSql(params);
        if (boundSql.hasAdditionalParameter(parameterMapping.getProperty())) {
            return boundSql.getAdditionalParameter(parameterMapping.getProperty());
        } else if (params == null) {
            return null;
        } else if (mappedStatement.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(params.getClass())) {
            return params;
        } else {
            MetaObject metaObject = configuration.newMetaObject(params);
            return metaObject.getValue(parameterMapping.getProperty());
        }
    }

    private String replaceParameter(String sql, Object value, JdbcType jdbcType, Class javaType) {
        String strValue = String.valueOf(value);
        if (jdbcType != null) {
            switch(jdbcType) {
                case BIT:
                case TINYINT:
                case SMALLINT:
                case INTEGER:
                case BIGINT:
                case FLOAT:
                case REAL:
                case DOUBLE:
                case NUMERIC:
                case DECIMAL:
                    break;
                case DATE:
                case TIME:
                case TIMESTAMP:
                default:
                    strValue = "'" + strValue + "'";
            }
        } else if (!Number.class.isAssignableFrom(javaType)) {
            strValue = "'" + strValue + "'";
        }

        return sql.replaceFirst("\\?", strValue);
    }

    private Method getDeclaredMethods(Class clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        Method[] var4 = methods;
        int var5 = methods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        throw new IllegalArgumentException("方法" + methodName + "不存在！");
    }

    private String getParamNameFromAnnotation(Method method, int i, String paramName) {
        Object[] paramAnnos = method.getParameterAnnotations()[i];
        Annotation[] var5 = (Annotation[]) paramAnnos;
        int var6 = paramAnnos.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object paramAnno = var5[var7];
            if (paramAnno instanceof Param) {
                paramName = ((Param)paramAnno).value();
            }
        }

        return paramName;
    }

    private Object wrapCollection(Object object) {
        HashMap map;
        if (object instanceof List) {
            map = Maps.newHashMap();
            map.put("list", object);
            return map;
        } else if (object != null && object.getClass().isArray()) {
            map = Maps.newHashMap();
            map.put("array", object);
            return map;
        } else {
            return object;
        }
    }
}

