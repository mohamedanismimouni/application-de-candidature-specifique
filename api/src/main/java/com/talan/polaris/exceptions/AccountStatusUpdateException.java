package com.talan.polaris.exceptions;

/**
 * AccountStatusUpdateException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class AccountStatusUpdateException extends ForbiddenException {

    private static final long serialVersionUID = -8514498089642163594L;

    public AccountStatusUpdateException() {
        super();
    }

    public AccountStatusUpdateException(Throwable cause) {
        super(cause);
    }

}
