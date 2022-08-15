package com.omega.cowalk.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    private static final String CONTENT_TYPE_JSON = "application/json";

    public static boolean isContentTypeJson(HttpServletRequest request) {
        return request.getContentType().contains(CONTENT_TYPE_JSON);
    }
}
