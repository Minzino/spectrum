package com.spectrum.exception.resolver;

import com.spectrum.common.exception.ErrorCodeAndMessage;
import com.spectrum.common.exception.SpectrumRuntimeException;

public class HttpServletRequestNullException extends SpectrumRuntimeException {

    public HttpServletRequestNullException() {
        super(ErrorCodeAndMessage.HTTP_SERVLET_REQUEST_NULL);
    }
}
