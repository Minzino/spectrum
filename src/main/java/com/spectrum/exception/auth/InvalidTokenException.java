package com.spectrum.exception.auth;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class InvalidTokenException extends SpectrumRuntimeException {

    public InvalidTokenException() {
        super(ErrorCodeAndMessage.INVALID_TOKEN);
    }
}
