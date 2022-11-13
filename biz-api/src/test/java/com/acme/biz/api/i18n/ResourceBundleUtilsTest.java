package com.acme.biz.api.i18n;

import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

/**
 * @Description: {@link ResourceBundleUtils} Test
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class ResourceBundleUtilsTest {

    @Test
    public void testJavaPropertiesResourceBundle() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("META-INF.Messages");
        System.out.println(resourceBundle.getString("my.name"));
    }

    @Test
    public void testJavaClassResourceBundle() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.acme.biz.api.i18n.HardCodeResourceBundle");
        System.out.println(resourceBundle.getString("my.name"));
    }
}
