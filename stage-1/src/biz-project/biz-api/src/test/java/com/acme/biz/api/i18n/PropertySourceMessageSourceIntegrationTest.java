package com.acme.biz.api.i18n;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.context.support.AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME;

/**
 * {@link PropertySourceMessageSource} Integration Test
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertySourceMessageSourceIntegrationTest.class)
public class PropertySourceMessageSourceIntegrationTest {

    @Primary
    @Bean(MESSAGE_SOURCE_BEAN_NAME)
    public static MessageSource messageSource(ConfigurableEnvironment environment) {
        return new PropertySourceMessageSource(environment);
    }

    @Autowired
    private MessageSource messageSource;

    @Test
    public void test() {
        String code = "my.name";
        Object[] args = new Object[0];
        assertEquals("不才人", messageSource.getMessage(code, args, Locale.getDefault()));
        assertEquals("elisha", messageSource.getMessage(code, args, Locale.ENGLISH));
        assertEquals("elisha zfy", messageSource.getMessage(code, args, Locale.US));
        assertEquals("default message", messageSource.getMessage("not.exist.code", args, "default message", Locale.US));
    }
}
