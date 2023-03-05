package com.spectrum.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeAndMessage {

    SERVER_ERROR("S501 - Internal Server Error", "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("F402 - Invalid Request", "요청 정보를 확인해주세요.", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND("P401 - Post Not Found", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeAndMessage(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
