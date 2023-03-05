package com.spectrum.common.exception;

import lombok.Getter;

@Getter
public class SpectrumApplicationException extends RuntimeException {

    private final String code;

    public SpectrumApplicationException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getMessage());
        this.code = errorCodeAndMessage.getCode();
    }
}
