package com.acme.biz.web.client.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: {@link RestTemplate} 配置类
 * @Author <a href="mailto:zfy_zang@163.com">Vincent</a>
 * @Modify
 * @since
 */
@Configuration(proxyBeanMethods = false)
@Import(ErrorClientHttpRequestInterceptor.class)
public class RestTemplateConfiguration {

    @Bean
    public ClientHttpRequestInterceptor validatingClientHttpRequestInterceptor(Validator validator) {
        return new ValidatingClientHttpRequestInterceptor(validator, mappingJackson2HttpMessageConverter());
    }

    @Bean
    public RestTemplate restTemplate(List<ClientHttpRequestInterceptor> interceptors) {
        List<HttpMessageConverter<?>> converters = Arrays.asList(mappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(converters);
        ClientHttpRequestFactory requestFactory = buildClientHttpRequestFactory(interceptors);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
        httpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpMessageConverter;
    }

    private ClientHttpRequestFactory buildClientHttpRequestFactory(List<ClientHttpRequestInterceptor> interceptors) {
        // TODO 替换 SimpleClientHttpRequestFactory
        ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        return new InterceptingClientHttpRequestFactory(requestFactory, interceptors);
    }
}
