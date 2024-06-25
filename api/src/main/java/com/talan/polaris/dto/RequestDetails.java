package com.talan.polaris.dto;

import java.io.Serializable;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.talan.polaris.utils.HttpUtils;

/**
 * RequestDetails.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class RequestDetails implements Serializable {

    private static final long serialVersionUID = 9186647901362958415L;

    private final String requestPath;
    private final String queryString;
    private final String requestMethod;
    private final String remoteHost;
    private final int remotePort;
    private final String clientIP;
    private final String userAgent;
    private final String locale;

    private RequestDetails(
            String requestPath,
            String queryString,
            String requestMethod,
            String remoteHost,
            int remotePort,
            String clientIP,
            String userAgent,
            String locale) {

        this.requestPath = requestPath;
        this.queryString = queryString;
        this.requestMethod = requestMethod;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.clientIP = clientIP;
        this.userAgent = userAgent;
        this.locale = locale;

    }

    public static RequestDetails fromHttpServletRequest(final HttpServletRequest request) {

        if (request == null)
            throw new NullPointerException("Expecting a non null HttpServletRequest argument");

        return new RequestDetails(
            HttpUtils.obtainRequestPath(request), 
            Optional.ofNullable(request.getQueryString()).orElse(""), 
            request.getMethod(), 
            request.getRemoteHost(), 
            request.getRemotePort(), 
            HttpUtils.obtainClientIP(request), 
            request.getHeader("User-Agent"), 
            request.getLocale().getLanguage()
        );

    }

    public String getRequestPath() {
        return this.requestPath;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public int getRemotePort() {
        return this.remotePort;
    }

    public String getClientIP() {
        return this.clientIP;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getLocale() {
        return this.locale;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("RequestDetails").append("\n")
                .append("requestPath       = ").append(this.requestPath).append("\n")
                .append("queryString       = ").append(this.queryString).append("\n")
                .append("requestMethod     = ").append(this.requestMethod).append("\n")
                .append("remoteHost        = ").append(this.remoteHost).append("\n")
                .append("remotePort        = ").append(this.remotePort).append("\n")
                .append("clientIP          = ").append(this.clientIP).append("\n")
                .append("userAgent         = ").append(this.userAgent).append("\n")
                .append("locale            = ").append(this.locale)
                .toString();

    }

}
