package com.spectrum.exception.post;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class InvalidSearchTypeException extends SpectrumRuntimeException {
    public InvalidSearchTypeException() {
        super(ErrorCodeAndMessage.INVALID_SEARCH_TYPE);
    }
}
