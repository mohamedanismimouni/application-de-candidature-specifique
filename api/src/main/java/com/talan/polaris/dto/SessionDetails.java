package com.talan.polaris.dto;

import java.io.Serializable;

/**
 * SessionDetails.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class SessionDetails implements Serializable {

    private static final long serialVersionUID = 6368367256271273659L;

    private final String sessionID;
    private final String sessionType;

    public SessionDetails(String sessionID, String sessionType) {
        this.sessionID = sessionID;
        this.sessionType = sessionType;
    }

    public String getSessionID() {
        return this.sessionID;
    }

    public String getSessionType() {
        return this.sessionType;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("SessionDetails").append("\n")
                .append("sessionID         = ").append(this.sessionID).append("\n")
                .append("sessionType       = ").append(this.sessionType)
                .toString();

    }

}
