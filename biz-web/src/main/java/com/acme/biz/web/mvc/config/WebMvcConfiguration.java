package com.acme.biz.web.mvc.config;

import com.acme.biz.web.mvc.method.annotation.ApiResponseHandlerMethodReturnValueHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@Configuration
public class WebMvcConfiguration {

    @Autowired
    public void resetRequestMappingHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        List<HandlerMethodReturnValueHandler> oldReturnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newReturnValueHandlers = new ArrayList<>(oldReturnValueHandlers);
        newReturnValueHandlers.add(0, new ApiResponseHandlerMethodReturnValueHandler());
        requestMappingHandlerAdapter.setReturnValueHandlers(newReturnValueHandlers);
    }
}
