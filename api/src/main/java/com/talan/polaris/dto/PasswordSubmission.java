package com.talan.polaris.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.talan.polaris.utils.validation.constraints.PasswordConfirmationConstraint;
import com.talan.polaris.utils.validation.constraints.PasswordConstraint;

/**
 * PasswordSubmission.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@PasswordConfirmationConstraint
public final class PasswordSubmission implements Serializable {

    private static final long serialVersionUID = 7763594691653950245L;

    @NotNull
    @PasswordConstraint
    private final String password;

    private final String passwordConfirmation;

    public PasswordSubmission(String password, String passwordConfirmation) {
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPasswordConfirmation() {
        return this.passwordConfirmation;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("PasswordSubmission").append("\n")
                .append("password               = [ SECRET ]").append("\n")
                .append("passwordConfirmation   = [ SECRET ]")
                .toString();

    }
    
}
