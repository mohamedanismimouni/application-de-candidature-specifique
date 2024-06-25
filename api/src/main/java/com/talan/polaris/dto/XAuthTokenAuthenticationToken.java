package com.talan.polaris.dto;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * XAuthTokenAuthenticationToken.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class XAuthTokenAuthenticationToken implements Authentication {

    private static final long serialVersionUID = -2069158098541161214L;

    private final String sessionID;
    private final RequestDetails requestDetails;

    public XAuthTokenAuthenticationToken(String sessionID, RequestDetails requestDetails) {
        this.sessionID = sessionID;
        if (requestDetails == null)
            throw new NullPointerException("Expecting a non null RequestDetails argument");
        this.requestDetails = requestDetails;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return this.sessionID;
    }

    @Override
    public Object getDetails() {
        return this.requestDetails;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("XAuthTokenAuthenticationToken").append("\n")
                .append("sessionID         = ").append(this.sessionID).append("\n")
                .append("requestDetails    = ").append(this.requestDetails)
                .toString();

    }
    
}
