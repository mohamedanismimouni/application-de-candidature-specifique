package com.talan.polaris.exceptions;

/**
 * ForbiddenException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 7421548453404067439L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

}
