package com.spectrum.common.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ErrorMapping {

    private final Map<Class<? extends Exception>, HttpStatus> exceptionHttpStatusMap;

    public ErrorMapping() {
        exceptionHttpStatusMap = new HashMap<>();
        exceptionHttpStatusMap.put(SpectrumRuntimeException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionHttpStatusMap.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(IllegalStateException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(UnsupportedOperationException.class, HttpStatus.BAD_REQUEST);
    }

    public HttpStatus getHttpStatusForException(Class<? extends Exception> exClass) {
        return exceptionHttpStatusMap.getOrDefault(exClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
