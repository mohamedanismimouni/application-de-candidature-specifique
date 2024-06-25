package com.talan.polaris.exceptions;

/**
 * ResourceNotFoundException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class ResourceNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -3003515927320077458L;

    private final String id;

    public ResourceNotFoundException() {
        super();
        this.id = null;
    }

    public ResourceNotFoundException(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

}
