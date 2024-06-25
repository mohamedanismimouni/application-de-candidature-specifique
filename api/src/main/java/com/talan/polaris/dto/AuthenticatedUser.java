package com.talan.polaris.dto;

import java.io.Serializable;
import java.security.Principal;


/**
 * AuthenticatedUser.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class AuthenticatedUser implements Principal, Serializable {

    private static final long serialVersionUID = -6077806154205460241L;

    private final Long id;
    private final String name;
    private final String email;
   // private final Collection<RoleEntity> role;

    private AuthenticatedUser(Long id, String name, String email /*Collection<RoleEntity> role*/) {
        this.id = id;
        this.name = name;
        this.email = email;
        //this.role = role;
    }

   /* public static AuthenticatedUser fromUserEntity(final UserEntity userEntity) {
        if (userEntity == null)
            throw new NullPointerException("Expecting a non null UserEntity argument");
        return new AuthenticatedUser(
            userEntity.getId(), 
            userEntity.getFirstName() + " " + userEntity.getLastName(), 
            userEntity.getEmail(), 
            userEntity.getRoles()
        );
    }*/

    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

   /* public Collection<RoleEntity> getRole() {
        return this.role;
    }*/

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("AuthenticatedUser").append("\n")
                .append("id                = ").append(this.id).append("\n")
                .append("name              = ").append(this.name).append("\n")
                .append("email             = ").append(this.email).append("\n")
                /*.append("role              = ").append(this.role)*/
                .toString();

    }

}
