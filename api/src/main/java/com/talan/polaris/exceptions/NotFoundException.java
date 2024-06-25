package com.talan.polaris.exceptions;

/**
 * NotFoundException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public abstract class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4003187797072492377L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
    
}
