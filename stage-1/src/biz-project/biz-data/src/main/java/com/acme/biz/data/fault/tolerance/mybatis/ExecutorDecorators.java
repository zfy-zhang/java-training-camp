package com.acme.biz.data.fault.tolerance.mybatis;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * TODO
 *
 * @Author zfy
 * @Date 2024/1/5
 **/
public class ExecutorDecorators implements Executor {

    private final List<ExecutorDecorator> decorators;

    private final int lastIndex;

    public ExecutorDecorators(Executor delegate, List<ExecutorDecorator> executorDecorators) {
        this.decorators = executorDecorators;
        this.decorators.forEach(decorators -> decorators.setDelegate(delegate));
        this.lastIndex = decorators.size() - 1;
    }


    @Override
    public int update(MappedStatement mappedStatement, Object parameter) throws SQLException {
        // N - 1
        for (int i = 0; i < lastIndex; i++) {
            ExecutorDecorator executorDecorator = decorators.get(i);
            executorDecorator.update(mappedStatement, parameter);
        }
        ExecutorDecorator executorDecorator = decorators.get(lastIndex);
        return executorDecorator.update(mappedStatement, parameter);
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return null;
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        return null;
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement mappedStatement, Object o, RowBounds rowBounds) throws SQLException {
        return null;
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return null;
    }

    @Override
    public void commit(boolean b) throws SQLException {

    }

    @Override
    public void rollback(boolean b) throws SQLException {

    }

    @Override
    public CacheKey createCacheKey(MappedStatement mappedStatement, Object o, RowBounds rowBounds, BoundSql boundSql) {
        return null;
    }

    @Override
    public boolean isCached(MappedStatement mappedStatement, CacheKey cacheKey) {
        return false;
    }

    @Override
    public void clearLocalCache() {

    }

    @Override
    public void deferLoad(MappedStatement mappedStatement, MetaObject metaObject, String s, CacheKey cacheKey, Class<?> aClass) {

    }

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public void close(boolean b) {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void setExecutorWrapper(Executor executor) {

    }
}
