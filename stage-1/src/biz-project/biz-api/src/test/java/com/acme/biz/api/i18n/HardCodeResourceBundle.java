package com.acme.biz.api.i18n;

import java.util.ListResourceBundle;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class HardCodeResourceBundle extends ListResourceBundle {

    private static final Object[][] contents = {
            {"my.name", "elisha zfy"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
