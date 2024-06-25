package com.talan.polaris.exceptions;

public class ResourceNotFoundLongException extends NotFoundException {
    private static final long serialVersionUID = -3003515927320077458L;

    private final Long id;

    public ResourceNotFoundLongException() {
        super();
        this.id = null;
    }

    public ResourceNotFoundLongException(Long id) {
        super();
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

}
