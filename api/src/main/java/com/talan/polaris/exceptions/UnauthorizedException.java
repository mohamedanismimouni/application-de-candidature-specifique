package com.talan.polaris.exceptions;

/**
 * UnauthorizedException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 4551435222494411533L;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

}
