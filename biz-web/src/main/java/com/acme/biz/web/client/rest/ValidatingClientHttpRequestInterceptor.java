package com.acme.biz.web.client.rest;

import com.acme.biz.api.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @Description: Bean Validation 校验 {@link ClientHttpRequestInterceptor} 实现
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public class ValidatingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final HttpMessageConverter[] converters;

    private final Validator validator;

    public ValidatingClientHttpRequestInterceptor(Validator validator, HttpMessageConverter... converters) {
        this.validator = validator;
        this.converters = converters;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = null;
        // 前置处理
        boolean valid = beforeExecute(request, body);
        HttpHeaders headers = request.getHeaders();
        headers.add("validation-result", Boolean.toString(valid));
        // 请求处理 (next interceptor)
        response = execution.execute(request, body);
        // 后置处理
        return afterExecute(response);
    }

    private boolean beforeExecute(HttpRequest request, byte[] body) {
        return validateBean(request, body);
    }

    private boolean validateBean(HttpRequest request, byte[] body) {
        // FastJson auto-type
        Class<?> bodyClass = resolveBodyClass(request.getHeaders());
        if (bodyClass != null) {
            HttpInputMessage httpInputMessage = new MappingJacksonInputMessage(new ByteArrayInputStream(body), request.getHeaders());
            MediaType mediaType = resolveMediaType(httpInputMessage);
            for (HttpMessageConverter converter : converters) {
                if (converter.canRead(bodyClass, mediaType)) {
                    try {
                        Object bean = converter.read(bodyClass, httpInputMessage);
                        Set<ConstraintViolation<Object>> violations = validator.validate(bean);
                        if (!violations.isEmpty()) {
                            return false;
                        }
                        // TODO
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return true;
    }

    private MediaType resolveMediaType(HttpInputMessage httpInputMessage) {
        HttpHeaders headers = httpInputMessage.getHeaders();
        return headers.getContentType();
    }

    private Class<?> resolveBodyClass(HttpHeaders httpHeaders) {
        List<String> classes = httpHeaders.get("body-class");
        if (!ObjectUtils.isEmpty(classes)) {
            String bodyClassName = classes.get(0);
            if (StringUtils.hasText(bodyClassName)) {
                return ClassUtils.resolveClassName(bodyClassName, null);
            }
        }
        return User.class;
    }

    private ClientHttpResponse afterExecute(ClientHttpResponse response) {
        return response;
    }
}
