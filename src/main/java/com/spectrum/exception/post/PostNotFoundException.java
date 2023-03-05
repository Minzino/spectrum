package com.spectrum.exception.post;

import com.spectrum.common.exception.SpectrumRuntimeException;
import com.spectrum.common.exception.ErrorCodeAndMessage;

public class PostNotFoundException extends SpectrumRuntimeException {

    public PostNotFoundException() {
        super(ErrorCodeAndMessage.POST_NOT_FOUND);
    }
}
