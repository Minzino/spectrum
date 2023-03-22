package com.spectrum.exception.comment;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class NotParentException extends SpectrumRuntimeException {

    public NotParentException() {
        super(ErrorCodeAndMessage.NOT_PARENT_EXCEPTION);
    }
}
