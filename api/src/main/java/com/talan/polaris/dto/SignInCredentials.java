package com.talan.polaris.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.talan.polaris.utils.validation.constraints.EmailConstraint;

/**
 * SignInCredentials.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class SignInCredentials implements Serializable {

    private static final long serialVersionUID = 6135312062188277856L;

    @NotNull
    @EmailConstraint
    private final String email;

    @NotBlank
    private final String password;

    public SignInCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("SignInCredentials").append("\n")
                .append("email             = ").append(this.email).append("\n")
                .append("password          = [ SECRET ]")
                .toString();

    }

}
