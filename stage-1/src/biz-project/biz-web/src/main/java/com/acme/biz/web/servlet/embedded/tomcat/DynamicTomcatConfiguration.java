package com.acme.biz.web.servlet.embedded.tomcat;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.environment.EnvironmentManager;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * 动态 Tomcat 配置实现
 *
 * @see ConfigurationPropertiesRebinder
 * @see EnvironmentManager
 * @see ConfigurationPropertiesBinder
 * @Author zfy
 * @Date 2024/1/15
 **/
@Configuration
public class DynamicTomcatConfiguration implements TomcatProtocolHandlerCustomizer {

    private AbstractProtocol protocol;

    private volatile ServerProperties originalServerProperties;

    @Autowired
    private ServerProperties serverProperties;

    @Deprecated
    @PostConstruct
    public void init() {
        this.originalServerProperties = new ServerProperties();
        BeanUtils.copyProperties(serverProperties, originalServerProperties);
    }

    @EventListener(EnvironmentChangeEvent.class)
    public void onEnvironmentChangeEvent(EnvironmentChangeEvent event) {

        // 需要排除非关注 keys
        // server.tomcat.*
        Set<String> keys = event.getKeys();
        // server.tomcat.threads.minSpare
        // server.tomcat.threads.max
        if (keys.contains("server.tomcat.threads.max")) {
            setMaxThreads();
        } else if (keys.contains("server.tomcat.threads.minSpare")) { // 不足：无法匹配 server.tomcat.threads.min-spare
            setMinSpareThreads();
        }
    }
    @EventListener(EnvironmentChangeEvent.class)
    public void onEnvironmentChangeEvent2(EnvironmentChangeEvent event) {

        ServerProperties.Tomcat.Threads originalThreads = originalServerProperties.getTomcat().getThreads();
        ServerProperties.Tomcat.Threads threads = serverProperties.getTomcat().getThreads();
        if (originalThreads.getMinSpare() != threads.getMinSpare()) {
            setMinSpareThreads();
        }
    }


//    @EventListener(EnvironmentChangeEvent.class)
    public void updateOriginalServerProperties() {
        buildOriginalServerProperties();
    }

    @Deprecated
    public void buildOriginalServerProperties() {
        ServerProperties newServerProperties = new ServerProperties();
        BeanUtils.copyProperties(serverProperties, newServerProperties);
        this.originalServerProperties = newServerProperties;
    }

    private void setMaxThreads() {
        int maxThreads = serverProperties.getTomcat().getThreads().getMax();
        protocol.setMaxThreads(maxThreads);
    }

    private void setMinSpareThreads() {
        int minSpareThreads = serverProperties.getTomcat().getThreads().getMinSpare();
        protocol.setMinSpareThreads(minSpareThreads);
        buildOriginalServerProperties();
    }

    @Override
    public void customize(ProtocolHandler protocolHandler) {
        if (protocolHandler instanceof AbstractProtocol) {
            this.protocol = (AbstractProtocol) protocolHandler;
        }
    }
}
