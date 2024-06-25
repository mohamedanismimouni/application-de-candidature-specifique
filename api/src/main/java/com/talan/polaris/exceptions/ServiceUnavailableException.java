package com.talan.polaris.exceptions;

/**
 * ServiceUnavailableException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class ServiceUnavailableException extends RuntimeException {

    private static final long serialVersionUID = -3929016116601851209L;

    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }

}
