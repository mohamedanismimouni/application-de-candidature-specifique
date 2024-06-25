package com.talan.polaris.exceptions;

/**
 * PasswordResetException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class PasswordResetException extends ForbiddenException {

    private static final long serialVersionUID = 6497962508986853049L;

    public PasswordResetException() {
        super();
    }

    public PasswordResetException(Throwable cause) {
        super(cause);
    }

}
