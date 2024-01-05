package com.acme.biz.data.fault.tolerance.mybatis;

import io.vavr.CheckedFunction0;
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
 * Executor 静态拦截实现（包装器）
 *
 * @Author zfy
 * @Date 2024/1/5
 **/
public abstract class ExecutorDecorator implements Executor {


    private Executor delegate;

    public ExecutorDecorator() {
    }

    public void setDelegate(Executor delegate) {
        this.delegate = delegate;
    }

    public <R> R doInDelegate(MappedStatement mappedStatement, CheckedFunction0<R> action) throws SQLException {
        R result = null;
        before(mappedStatement);
        try {
            result = action.apply();
        } catch (Throwable e) {
            if (e instanceof SQLException) {
                throw (SQLException) e;
            }
        } finally {
            after(mappedStatement);
        }
        return result;
    }

    protected abstract void before(MappedStatement mappedStatement);

    protected abstract void after(MappedStatement mappedStatement);

    @Override
    public int update(MappedStatement mappedStatement, Object parameter) throws SQLException {
        return doInDelegate(mappedStatement, () -> delegate.update(mappedStatement, parameter));
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException {
        return delegate.query(mappedStatement, o, rowBounds, resultHandler, cacheKey, boundSql);
    }

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Object o, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        return delegate.query(mappedStatement, o, rowBounds, resultHandler);
    }

    @Override
    public <E> Cursor<E> queryCursor(MappedStatement mappedStatement, Object o, RowBounds rowBounds) throws SQLException {
        return delegate.queryCursor(mappedStatement, o, rowBounds);
    }

    @Override
    public List<BatchResult> flushStatements() throws SQLException {
        return delegate.flushStatements();
    }

    @Override
    public void commit(boolean b) throws SQLException {
        delegate.commit(b);
    }

    @Override
    public void rollback(boolean b) throws SQLException {
        delegate.rollback(b);
    }

    @Override
    public CacheKey createCacheKey(MappedStatement mappedStatement, Object o, RowBounds rowBounds, BoundSql boundSql) {
        return delegate.createCacheKey(mappedStatement, o, rowBounds, boundSql);
    }

    @Override
    public boolean isCached(MappedStatement mappedStatement, CacheKey cacheKey) {
        return delegate.isCached(mappedStatement, cacheKey);
    }

    @Override
    public void clearLocalCache() {
        delegate.clearLocalCache();
    }

    @Override
    public void deferLoad(MappedStatement mappedStatement, MetaObject metaObject, String s, CacheKey cacheKey, Class<?> aClass) {
        delegate.deferLoad(mappedStatement, metaObject, s, cacheKey, aClass);
    }

    @Override
    public Transaction getTransaction() {
        return delegate.getTransaction();
    }

    @Override
    public void close(boolean b) {
        delegate.close(b);
    }

    @Override
    public boolean isClosed() {
        return delegate.isClosed();
    }

    @Override
    public void setExecutorWrapper(Executor executor) {
        delegate.setExecutorWrapper(executor);
    }
}
