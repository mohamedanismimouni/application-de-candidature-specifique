package com.talan.polaris.exceptions;

/**
 * MailSendingException.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class MailSendingException extends ServiceUnavailableException {

    private static final long serialVersionUID = -5183653746483670470L;

    public MailSendingException() {
        super();
    }

    public MailSendingException(Throwable cause) {
        super(cause);
    }

}
