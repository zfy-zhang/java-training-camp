package com.acme.biz.api.fault.tolerance.openfeign;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * TODO
 *
 * @Author zfy
 * @Date 2024/1/5
 **/
@Deprecated
public class BulkHeadRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            Request request = requestTemplate.request();
            RequestTemplate template = request.requestTemplate();
            String resourceName = request.httpMethod() + ":" + requestTemplate.path();
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
    }
}
