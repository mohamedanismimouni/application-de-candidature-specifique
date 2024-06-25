package com.talan.polaris.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpUtils.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class HttpUtils {

    private static final String[] IP_HEADER_CANDIDATES = { 
        "X-Forwarded-For", 
        "HTTP_X_FORWARDED_FOR", 
        "HTTP_X_FORWARDED", 
        "HTTP_FORWARDED_FOR", 
        "HTTP_FORWARDED", 
        "FORWARDED", 
        "Proxy-Client-IP", 
        "WL-Proxy-Client-IP", 
        "CLIENT_IP",
        "HTTP_CLIENT_IP", 
        "HTTP_X_CLUSTER_CLIENT_IP", 
        "HTTP_X_REAL_IP", 
        "HTTP_FROM", 
        "HTTP_X_COMING_FROM", 
        "HTTP_COMING_FROM", 
        "HTTP_PROXY_CONNECTION", 
        "HTTP_VIA", 
        "REMOTE_ADDR" 
    };

    private HttpUtils() {

    }

    public static String obtainRequestPath(final HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    public static String obtainClientIP(final HttpServletRequest request) {
        for (String ipHeaderCandidate : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(ipHeaderCandidate);
            if (ipList != null && ipList.length() != 0 && !("unknown").equalsIgnoreCase(ipList))
                return ipList.split(",")[0];
        }
        return request.getRemoteAddr();
    }

}
