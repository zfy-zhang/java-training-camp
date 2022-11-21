package com.acme.biz.web.servlet.embedded.tomcat;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @Description: 自定义 {@link WebServerFactoryCustomizer}
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since TODO
 */
@Component
public class MyTomcatServletWebServerFactory implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addProtocolHandlerCustomizers(new MyTomcatProtocolHandlerCustomizer(factory));
    }
}
