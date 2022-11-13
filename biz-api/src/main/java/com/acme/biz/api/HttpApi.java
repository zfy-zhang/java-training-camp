package com.acme.biz.api;

import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
public abstract class HttpApi<T> {

    @Valid
    private Map<String, String> headers;

    @Valid
    private MultiValueMap<String, String> metadata;

    @Valid
    private T body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public MultiValueMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(MultiValueMap<String, String> metadata) {
        this.metadata = metadata;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
