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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        log.error("Unexpected error occurred.", ex);
        return new ErrorResponse("SERVER_ERROR", "Unexpected error occurred.");
    }

    @ExceptionHandler(SpectrumRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(SpectrumRuntimeException ex) {
        log.error("Error code: {}, message: {}", ex.getErrorCode(), ex.getErrorMessage());
        ErrorCodeAndMessage error = ex.getErrorCodeAndMessage();

        ErrorResponse errorResponse = new ErrorResponse(error.getCode(), error.getMessage());
        return ResponseEntity.status(error.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getFieldErrors().stream()
            .map(fieldError -> String.format("%s %s", fieldError.getField(),
                fieldError.getDefaultMessage()))
            .collect(Collectors.toList());

        return new ErrorResponse("INVALID_REQUEST", "Invalid request", errors);
    }
}
