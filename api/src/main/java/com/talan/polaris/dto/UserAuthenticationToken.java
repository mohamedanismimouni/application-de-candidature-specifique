
package com.talan.polaris.dto;

import java.util.Collection;

import com.talan.polaris.entities.CollaboratorEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


/**
 * UserAuthenticationToken.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */

public final class UserAuthenticationToken implements Authentication {

    private static final long serialVersionUID = 1326818658570722878L;

    private final AuthenticatedUser authenticatedUser;
    private final SessionDetails sessionDetails;
    private final RequestDetails requestDetails;
    private CollaboratorEntity user;

    public UserAuthenticationToken(
            AuthenticatedUser authenticatedUser,
            SessionDetails sessionDetails,
            RequestDetails requestDetails,
            CollaboratorEntity user) {

        if (authenticatedUser == null)
            throw new NullPointerException("Expecting a non null AuthenticatedUser argument");
        this.authenticatedUser = authenticatedUser;

        if (sessionDetails == null)
            throw new NullPointerException("Expecting a non null SessionDetails argument");
        this.sessionDetails = sessionDetails;

        if (requestDetails == null)
            throw new NullPointerException("Expecting a non null RequestDetails argument");
        this.requestDetails = requestDetails;
        this.user = user;

    }

    @Override
    public String getName() {
        return this.authenticatedUser.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       /* Collection<RoleEntity> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getLabel().getProfileType()));
        }

        return authorities;

  return Collections.singletonList(
                new SimpleGrantedAuthority(this.authenticatedUser.getRole()));*/
        return null;

    }

    @Override
    public Object getCredentials() {
        return this.sessionDetails;
    }

    @Override
    public Object getDetails() {
        return this.requestDetails;
    }

    @Override
    public Object getPrincipal() {
        return this.authenticatedUser;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("UserAuthenticationToken").append("\n")
                .append("authenticatedUser = ").append(this.authenticatedUser).append("\n")
                .append("sessionDetails    = ").append(this.sessionDetails).append("\n")
                .append("requestDetails    = ").append(this.requestDetails)
                .toString();

    }

}

