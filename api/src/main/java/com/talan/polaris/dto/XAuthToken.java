package com.talan.polaris.dto;

import java.io.Serializable;

/**
 * XAuthToken.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class XAuthToken implements Serializable {

    private static final long serialVersionUID = 438549289944962940L;

    private final String sessionID;

    public XAuthToken(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("XAuthToken").append("\n")
                .append("sessionID         = ").append(this.sessionID)
                .toString();

    }

}
