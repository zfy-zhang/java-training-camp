package com.acme.biz.api.redis;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Redis 命令执行拦截器选择器
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since TODO
 **/
public class RedisCommandInterceptorSelector implements ImportSelector, BeanClassLoaderAware {

    private ClassLoader classLoader;

    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return EnableRedisIntercepting.class;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> beanNames = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(), classLoader);
        return StringUtils.toStringArray(beanNames);
    }
}
