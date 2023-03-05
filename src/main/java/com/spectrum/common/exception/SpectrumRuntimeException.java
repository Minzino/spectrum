package com.spectrum.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpectrumRuntimeException extends RuntimeException {

    private final ErrorCodeAndMessage errorCodeAndMessage;

    public SpectrumRuntimeException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getMessage());
        this.errorCodeAndMessage = errorCodeAndMessage;
    }

    public String getErrorCode() {
        return errorCodeAndMessage.getCode();
    }

    public String getErrorMessage() {
        return errorCodeAndMessage.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return errorCodeAndMessage.getHttpStatus();
    }
}
