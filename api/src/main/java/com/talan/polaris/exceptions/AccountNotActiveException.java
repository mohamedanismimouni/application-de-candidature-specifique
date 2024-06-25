package com.talan.polaris.exceptions;

/**
 * AccountNotActiveException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class AccountNotActiveException extends ForbiddenException {

    private static final long serialVersionUID = -1229285287872519480L;

    public AccountNotActiveException() {
        super();
    }

    public AccountNotActiveException(Throwable cause) {
        super(cause);
    }

}
