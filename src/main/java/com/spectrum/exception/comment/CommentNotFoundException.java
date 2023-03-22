package com.spectrum.exception.comment;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class CommentNotFoundException extends SpectrumRuntimeException {

    public CommentNotFoundException() {
        super(ErrorCodeAndMessage.COMMENT_NOT_FOUND_EXCEPTION);
    }
}
