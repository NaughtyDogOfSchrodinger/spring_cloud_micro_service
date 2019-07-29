package com.jianghu.mscore.data.util;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlMapper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private MSUtils msUtils;

    public SqlMapper() {
    }

    private <T> T getOne(List<T> list) {
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    public Map<String, Object> selectOne(String sql) {
        List<Map<String, Object>> list = this.selectList(sql);
        return (Map)this.getOne(list);
    }

    public Map<String, Object> selectOne(String sql, Object value) {
        List<Map<String, Object>> list = this.selectList(sql, value);
        return (Map)this.getOne(list);
    }

    public <T> T selectOne(String sql, Class<T> resultType) {
        List<T> list = this.selectList(sql, resultType);
        return this.getOne(list);
    }

    public <T> T selectOne(String sql, Object value, Class<T> resultType) {
        List<T> list = this.selectList(sql, value, resultType);
        return this.getOne(list);
    }

    public List<Map<String, Object>> selectList(String sql) {
        String msId = this.msUtils.select(sql);
        return this.sqlSessionFactory.openSession().selectList(msId);
    }

    public List<Map<String, Object>> selectList(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = this.msUtils.selectDynamic(sql, parameterType);
        return this.sqlSessionFactory.openSession().selectList(msId, value);
    }

    public <T> List<T> selectList(String sql, Class<T> resultType) {
        String msId;
        if (resultType == null) {
            msId = this.msUtils.select(sql);
        } else {
            msId = this.msUtils.select(sql, resultType);
        }

        return this.sqlSessionFactory.openSession().selectList(msId);
    }

    public <T> List<T> selectList(String sql, Object value, Class<T> resultType) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId;
        if (resultType == null) {
            msId = this.msUtils.selectDynamic(sql, parameterType);
        } else {
            msId = this.msUtils.selectDynamic(sql, parameterType, resultType);
        }

        return this.sqlSessionFactory.openSession().selectList(msId, value);
    }

    public int insert(String sql) {
        String msId = this.msUtils.insert(sql);
        return this.sqlSessionFactory.openSession().insert(msId);
    }

    public int insert(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = this.msUtils.insertDynamic(sql, parameterType);
        return this.sqlSessionFactory.openSession().insert(msId, value);
    }

    public int update(String sql) {
        String msId = this.msUtils.update(sql);
        return this.sqlSessionFactory.openSession().update(msId);
    }

    public int update(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = this.msUtils.updateDynamic(sql, parameterType);
        return this.sqlSessionFactory.openSession().update(msId, value);
    }

    public int delete(String sql) {
        String msId = this.msUtils.delete(sql);
        return this.sqlSessionFactory.openSession().delete(msId);
    }

    public int delete(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = this.msUtils.deleteDynamic(sql, parameterType);
        return this.sqlSessionFactory.openSession().delete(msId, value);
    }
}

