package com.spectrum.common.exception;

import com.spectrum.common.dto.ErrorResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Map<Class<? extends Exception>, HttpStatus> exceptionHttpStatusMap;

    public GlobalExceptionHandler() {
        exceptionHttpStatusMap = new HashMap<>();
        exceptionHttpStatusMap.put(SpectrumRuntimeException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        exceptionHttpStatusMap.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(IllegalStateException.class, HttpStatus.BAD_REQUEST);
        exceptionHttpStatusMap.put(UnsupportedOperationException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error occurred.", ex);
        HttpStatus httpStatus = exceptionHttpStatusMap.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", "Unexpected error occurred.");
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(SpectrumRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(SpectrumRuntimeException ex) {
        log.error("Error code: {}, message: {}", ex.getErrorCode(), ex.getErrorMessage());
        ErrorCodeAndMessage error = ex.getErrorCodeAndMessage();

        ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage());
        return ResponseEntity.status(error.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getFieldErrors().stream()
            .map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", "Invalid request", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
