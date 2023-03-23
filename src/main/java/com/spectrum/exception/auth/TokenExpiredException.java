package com.spectrum.exception.auth;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class TokenExpiredException extends SpectrumRuntimeException {

    public TokenExpiredException() {
        super(ErrorCodeAndMessage.TOKEN_EXPIRED);
    }
}
