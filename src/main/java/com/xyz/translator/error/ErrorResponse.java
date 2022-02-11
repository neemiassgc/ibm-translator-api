package com.xyz.translator.error;

import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ErrorResponse {

    private String message;
    private Throwable cause;
    private int statusCode;

    public ErrorResponse(final ServiceResponseException sre) {
        this(sre.getMessage(), sre.getCause(), sre.getStatusCode());
    }

    public ErrorResponse(String message, Throwable cause, int statusCode) {
        this.message = message;
        this.cause = cause;
        this.statusCode = statusCode;
    }
}
