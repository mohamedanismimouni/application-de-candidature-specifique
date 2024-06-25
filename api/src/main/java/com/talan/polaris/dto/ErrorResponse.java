package com.talan.polaris.dto;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.http.HttpStatus;

/**
 * ErrorResponse.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 2131115374889012473L;

    private final int status;
    private final String error;
    private final String message;
    private final String details;
    private final String path;
    private final Instant timestamp;

    public static class ErrorResponseBuilder {

        private final int status;
        private final String error;
        private final String path;

        private String message = "";
        private String details = "";

        public ErrorResponseBuilder(HttpStatus httpStatus, String path) {
            this.status = httpStatus.value();
            this.error = httpStatus.getReasonPhrase();
            this.path = path;
        }

        public ErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
        
    }

    private ErrorResponse(ErrorResponseBuilder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
        this.details = builder.details;
        this.path = builder.path;
        this.timestamp = Instant.now();
    }

    public int getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }

    public String getPath() {
        return this.path;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("ErrorResponse").append("\n")
                .append("status            = ").append(this.status).append("\n")
                .append("error             = ").append(this.error).append("\n")
                .append("message           = ").append(this.message).append("\n")
                .append("details           = ").append(this.details).append("\n")
                .append("path              = ").append(this.path).append("\n")
                .append("timestamp         = ").append(this.timestamp)
                .toString();

    }

}
