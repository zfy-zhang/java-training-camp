package com.acme.biz.web.servlet.embedded.tomcat;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

/**
 * @Description: 自定义 Tomcat {@link ProtocolHandler} 实现
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class MyTomcatProtocolHandlerCustomizer implements TomcatProtocolHandlerCustomizer {

    private final TomcatServletWebServerFactory factory;

    public MyTomcatProtocolHandlerCustomizer(TomcatServletWebServerFactory factory) {
        this.factory = factory;
    }

    @Override
    public void customize(ProtocolHandler protocolHandler) {
        if (protocolHandler instanceof AbstractProtocol) {
            AbstractProtocol protocol = (AbstractProtocol) protocolHandler;
            protocol.setMaxThreads(100);
        }
    }
}
