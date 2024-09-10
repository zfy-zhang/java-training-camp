package com.acme.biz.api.micrometer.binder.servo;

import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.StringUtils;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.ToDoubleFunction;

/**
 * Netflix Servo {@link MeterBinder}
 * @author: <a href="mailto:zfy_zang@163.com">elisha</a>
 * @since: 1.0.0
 */
public class ServoMetrics implements MeterBinder, ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ServoMetrics.class);

    private static final String OBJECT_NAME_PATTERN = "com.netflix.servo:*";

    private MeterRegistry meterRegistry;

    private ClassLoader classLoader;

    private ConversionService conversionService;

    private MBeanServer mBeanServer;

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initClassLoader(event);
        initConversionService(event);
        registerServoMetrics();
    }

    private void registerServoMetrics() {
        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = findServoMBeanObjectNames();
        for (ObjectName objectName : objectNames) {
            registerServoMetrics(objectName);
        }
    }

    private void registerServoMetrics(ObjectName objectName) {
        try {
            MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);
            String type = objectName.getKeyProperty("type");
            String name = objectName.getKeyProperty("name");
            String className = objectName.getKeyProperty("class");

            MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
            for (MBeanAttributeInfo attribute : attributes) {
                String attributeName = attribute.getName();
                String meterName = buildMeterName(objectName, attributeName);
                ToDoubleFunction<MBeanServer> toDoubleFunction = mbs -> {
                    Double value = null;
                    try {
                        Object attributeValue = mbs.getAttribute(objectName, attributeName);
                        value = conversionService.convert(attributeValue, Double.class);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                    return value;
                };

                switch(type) {
                    case "COUNTER":
                        FunctionCounter.builder(meterName, mBeanServer, toDoubleFunction)
                                .tags("name", name, "className", className)
                                .register(meterRegistry);
                        break;
                    case "GAUGE":
                        Gauge.builder(meterName, mBeanServer, toDoubleFunction)
                                .tags("name", name, "className", className)
                                .register(meterRegistry);
                        break;
                    case "NORMALIZED":
                        // TODO
                        break;
                    default:
                        // TODO
                        break;
                }
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String buildMeterName(ObjectName objectName, String attributeName) {
        String type = objectName.getKeyProperty("type");
        String name = objectName.getKeyProperty("name");
        String className = objectName.getKeyProperty("class");
        String id = objectName.getKeyProperty("id");

        // ${type}.${class}.${id}.${name}.${attributeName}
        StringJoiner stringJoiner = new StringJoiner(".");
        appendIfPresent(stringJoiner, type)
                .appendIfPresent(stringJoiner, name)
                .appendIfPresent(stringJoiner, className)
                .appendIfPresent(stringJoiner, id);
        return stringJoiner.toString();
    }

    private ServoMetrics appendIfPresent(StringJoiner stringJoiner, String value) {
        if (StringUtils.hasText(value)) {
            stringJoiner.add(value);
        }
        return this;
    }

    private Set<ObjectName> findServoMBeanObjectNames() {
        Set<ObjectName> objectNames = Collections.emptySet();

        try {
            ObjectName objectName = new ObjectName(OBJECT_NAME_PATTERN);
            objectNames = mBeanServer.queryNames(objectName, objectName);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
        return objectNames;
    }

    private void initConversionService(ApplicationReadyEvent event) {
        this.conversionService = event.getApplicationContext().getEnvironment().getConversionService();
    }

    private void initClassLoader(ApplicationReadyEvent event) {
        this.classLoader = event.getApplicationContext().getClassLoader();
    }
}
