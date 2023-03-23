package com.spectrum.exception.user;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class UserNotFoundException extends SpectrumRuntimeException {

    public UserNotFoundException() {
        super(ErrorCodeAndMessage.USER_NOT_FOUND);
    }
}
