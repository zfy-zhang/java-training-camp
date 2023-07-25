package com.acme.biz.api.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mock.env.MockEnvironment;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link PropertySourceMessageSource} Test
 *
 * @author <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since 1.0.0
 **/
public class PropertySourceMessageSourceTest {

    private PropertySourceMessageSource propertySourceMessageSource;

    @BeforeEach
    public void init() throws Exception {
        ConfigurableEnvironment environment = new MockEnvironment();
        propertySourceMessageSource = new PropertySourceMessageSource(environment);
        propertySourceMessageSource.afterPropertiesSet();
    }

    @Test
    public void test() {
        String code = "my.name";
        Object[] args = new Object[0];
        assertEquals("不才人", propertySourceMessageSource.getMessage(code,args, Locale.getDefault()));
        assertEquals("elisha", propertySourceMessageSource.getMessage(code,args, Locale.ENGLISH));
        assertEquals("elisha zfy", propertySourceMessageSource.getMessage(code,args, Locale.US));
        assertEquals("default message", propertySourceMessageSource.getMessage("not.exist.code", args, "default message", Locale.US));
    }
}
