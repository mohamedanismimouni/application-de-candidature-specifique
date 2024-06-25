package com.talan.polaris.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * OnboardingHint.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class OnboardingHint implements Serializable {

    private static final long serialVersionUID = -6125542453386800287L;

    private final int secretWordLength;
    private final Collection<String> secretWordPartsHoldersNames;

    public OnboardingHint(
            int secretWordLength,
            Collection<String> secretWordPartsHoldersNames) {

        this.secretWordLength = secretWordLength;
        this.secretWordPartsHoldersNames = secretWordPartsHoldersNames;

    }

    public int getSecretWordLength() {
        return this.secretWordLength;
    }

    public Collection<String> getSecretWordPartsHoldersNames() {
        return this.secretWordPartsHoldersNames;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("OnboardingHint").append("\n")
                .append("length            = ").append(this.secretWordLength).append("\n")
                .append("holders           = ").append(this.secretWordPartsHoldersNames)
                .toString();

    }

}
