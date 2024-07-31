package com.acme.biz.web.i18;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.springframework.context.support.AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME;

/**
 * {@link BeanPostProcessor} 实现，为 {@link LocalValidatorFactoryBean} 提前设置 {@link MessageSource}
 *
 * @Author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @Modify:
 * @since: 1.0.0
 */
public class LocalValidatorFactoryBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private MessageSource messageSource;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        // org.springframework.context.support.AbstractApplicationContext.initMessageSource
        this.messageSource = beanFactory.getBean(MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalValidatorFactoryBean) {
            LocalValidatorFactoryBean localValidatorFactoryBean = (LocalValidatorFactoryBean) bean;
            localValidatorFactoryBean.setValidationMessageSource(messageSource);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
