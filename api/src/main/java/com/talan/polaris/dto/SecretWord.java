package com.talan.polaris.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SecretWord.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public final class SecretWord implements Serializable {

    private static final long serialVersionUID = -4899374658998452481L;

    @NotBlank
    private final String value;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SecretWord(@JsonProperty("value") String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {

        return new StringBuilder()
                .append("\n").append("SecretWord").append("\n")
                .append("value             = ").append(this.value)
                .toString();

    }

}
