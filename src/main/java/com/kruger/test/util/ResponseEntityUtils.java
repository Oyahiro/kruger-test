package com.kruger.test.util;

import com.kruger.test.enums.ResponseHeader;
import org.springframework.http.HttpHeaders;

public class ResponseEntityUtils {

    public static <T> HttpHeaders generateErrorHeaders(Class<T> entity, Exception exception) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(ResponseHeader.ENTITY.name(), entity.getSimpleName());
        responseHeaders.set(ResponseHeader.ERROR_MESSAGE.name(), exception.getMessage());
        return responseHeaders;
    }

}
