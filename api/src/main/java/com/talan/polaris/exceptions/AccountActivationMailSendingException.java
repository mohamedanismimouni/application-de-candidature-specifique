package com.talan.polaris.exceptions;

/**
 * AccountActivationMailSendingException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class AccountActivationMailSendingException extends ForbiddenException {

    private static final long serialVersionUID = 6497962508986853049L;

    public AccountActivationMailSendingException() {
        super();
    }

    public AccountActivationMailSendingException(Throwable cause) {
        super(cause);
    }

}
