package com.talan.polaris.exceptions;

/**
 * AccountNotFoundException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class AccountNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1208614287249452122L;

    private final String email;

    public AccountNotFoundException(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

}
