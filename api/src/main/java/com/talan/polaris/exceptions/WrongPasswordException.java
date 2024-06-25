package com.talan.polaris.exceptions;

/**
 * WrongPasswordException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class WrongPasswordException extends UnauthorizedException {

    private static final long serialVersionUID = -5183653746483670470L;

    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(Throwable cause) {
        super(cause);
    }

}
