package com.spectrum.common.exception;

import com.spectrum.common.dto.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final ErrorMapping errorMapping;

    public GlobalExceptionHandler(ErrorMapping errorMapping) {
        this.errorMapping = errorMapping;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error occurred.", ex);
        HttpStatus httpStatus = errorMapping.getHttpStatusForException(ex.getClass());

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
