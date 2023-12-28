package com.acme.biz.api.openfeign;

import com.acme.biz.api.ApiResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

/**
 * TODO
 *
 * @Author zfy
 * @Date 2023/12/27
 **/
public class ApiResponseDecoder implements Decoder {

    private final Decoder decoder;

    public ApiResponseDecoder(Decoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // 服务端返回 ApiResponse，客户端需要 Boolean
        String contentType = getContentType(response);
        MediaType mediaType = MediaType.parseMediaType(contentType);
        String version = mediaType.getParameter("v");
        if (version == null) {
            Object object = decoder.decode(response, ApiResponse.class);
            if (object instanceof ApiResponse) {
                return ApiResponse.class.cast(object).getBody();
            }
        }
        return decoder.decode(response, type);
    }

    private String getContentType(Response response) {
        Collection<String> types = response.headers().getOrDefault("Content-Type", Arrays.asList("application/json;v=3"));
        return types.iterator().next();
    }
}
