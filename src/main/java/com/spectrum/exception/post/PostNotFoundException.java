package com.spectrum.exception.post;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumApplicationException;

public class PostNotFoundException extends SpectrumApplicationException {

    public PostNotFoundException() {
        super(ErrorCodeAndMessage.POST_NOT_FOUND);
    }
}
