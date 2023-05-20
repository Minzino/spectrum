package com.spectrum.exception.post;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class InvalidPaginationException extends SpectrumRuntimeException {

    public InvalidPaginationException() {
        super(ErrorCodeAndMessage.INVALID_PAGE_OR_SIZE);
    }
}
