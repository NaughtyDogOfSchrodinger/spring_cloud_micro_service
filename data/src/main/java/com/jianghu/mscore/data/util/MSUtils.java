package com.jianghu.mscore.data.util;

import java.util.ArrayList;
import java.util.Map;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MSUtils {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    MSUtils() {
    }

    private String newMsId(String sql, SqlCommandType sqlCommandType) {
        return sqlCommandType.toString() + "." + sql.hashCode();
    }

    private boolean hasMappedStatement(String msId) {
        return this.sqlSessionFactory.getConfiguration().hasStatement(msId, false);
    }

    private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
        MappedStatement ms = (new Builder(this.sqlSessionFactory.getConfiguration(), msId, sqlSource, SqlCommandType.SELECT)).resultMaps(new ArrayList<ResultMap>() {
            {
                this.add((new org.apache.ibatis.mapping.ResultMap.Builder(MSUtils.this.sqlSessionFactory.getConfiguration(), "defaultResultMap", resultType, new ArrayList(0))).build());
            }
        }).build();
        this.sqlSessionFactory.getConfiguration().addMappedStatement(ms);
    }

    private void newUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType) {
        MappedStatement ms = (new Builder(this.sqlSessionFactory.getConfiguration(), msId, sqlSource, sqlCommandType)).resultMaps(new ArrayList<ResultMap>() {
            {
                this.add((new org.apache.ibatis.mapping.ResultMap.Builder(MSUtils.this.sqlSessionFactory.getConfiguration(), "defaultResultMap", Integer.TYPE, new ArrayList(0))).build());
            }
        }).build();
        this.sqlSessionFactory.getConfiguration().addMappedStatement(ms);
    }

    public String select(String sql) {
        String msId = this.newMsId(sql, SqlCommandType.SELECT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            StaticSqlSource sqlSource = new StaticSqlSource(this.sqlSessionFactory.getConfiguration(), sql);
            this.newSelectMappedStatement(msId, sqlSource, Map.class);
            return msId;
        }
    }

    public String selectDynamic(String sql, Class<?> parameterType) {
        String msId = this.newMsId(sql + parameterType, SqlCommandType.SELECT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            SqlSource sqlSource = this.sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(this.sqlSessionFactory.getConfiguration(), sql, parameterType);
            this.newSelectMappedStatement(msId, sqlSource, Map.class);
            return msId;
        }
    }

    public String select(String sql, Class<?> resultType) {
        String msId = this.newMsId(resultType + sql, SqlCommandType.SELECT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            StaticSqlSource sqlSource = new StaticSqlSource(this.sqlSessionFactory.getConfiguration(), sql);
            this.newSelectMappedStatement(msId, sqlSource, resultType);
            return msId;
        }
    }

    public String selectDynamic(String sql, Class<?> parameterType, Class<?> resultType) {
        String msId = this.newMsId(resultType + sql + parameterType, SqlCommandType.SELECT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            SqlSource sqlSource = this.sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(this.sqlSessionFactory.getConfiguration(), sql, parameterType);
            this.newSelectMappedStatement(msId, sqlSource, resultType);
            return msId;
        }
    }

    public String insert(String sql) {
        String msId = this.newMsId(sql, SqlCommandType.INSERT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            StaticSqlSource sqlSource = new StaticSqlSource(this.sqlSessionFactory.getConfiguration(), sql);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
            return msId;
        }
    }

    public String insertDynamic(String sql, Class<?> parameterType) {
        String msId = this.newMsId(sql + parameterType, SqlCommandType.INSERT);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            SqlSource sqlSource = this.sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(this.sqlSessionFactory.getConfiguration(), sql, parameterType);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
            return msId;
        }
    }

    public String update(String sql) {
        String msId = this.newMsId(sql, SqlCommandType.UPDATE);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            StaticSqlSource sqlSource = new StaticSqlSource(this.sqlSessionFactory.getConfiguration(), sql);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
            return msId;
        }
    }

    public String updateDynamic(String sql, Class<?> parameterType) {
        String msId = this.newMsId(sql + parameterType, SqlCommandType.UPDATE);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            SqlSource sqlSource = this.sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(this.sqlSessionFactory.getConfiguration(), sql, parameterType);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
            return msId;
        }
    }

    public String delete(String sql) {
        String msId = this.newMsId(sql, SqlCommandType.DELETE);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            StaticSqlSource sqlSource = new StaticSqlSource(this.sqlSessionFactory.getConfiguration(), sql);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            return msId;
        }
    }

    public String deleteDynamic(String sql, Class<?> parameterType) {
        String msId = this.newMsId(sql + parameterType, SqlCommandType.DELETE);
        if (this.hasMappedStatement(msId)) {
            return msId;
        } else {
            SqlSource sqlSource = this.sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(this.sqlSessionFactory.getConfiguration(), sql, parameterType);
            this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
            return msId;
        }
    }
}

