package com.talan.polaris.exceptions;

/**
 * AccountActivationException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class AccountActivationException extends ForbiddenException {

    private static final long serialVersionUID = 6497962508986853049L;

    public AccountActivationException() {
        super();
    }

    public AccountActivationException(Throwable cause) {
        super(cause);
    }

}
