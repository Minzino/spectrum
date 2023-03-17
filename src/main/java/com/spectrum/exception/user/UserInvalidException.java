package com.spectrum.exception.user;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class UserInvalidException extends SpectrumRuntimeException {

    public UserInvalidException() {
        super(ErrorCodeAndMessage.INVALID_USER);
    }
}
