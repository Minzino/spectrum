package com.spectrum.exception.post;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class NotAuthorException extends SpectrumRuntimeException {

    public NotAuthorException() {
        super(ErrorCodeAndMessage.NOT_AUTHOR);
    }
}
