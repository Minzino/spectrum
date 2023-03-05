package com.spectrum.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeAndMessage {

    SERVER_ERROR("S501 - Internal Server Error", "서버 에러입니다."),
    INVALID_REQUEST("F402 - Invalid Request", "요청 정보를 확인해주세요."),
    POST_NOT_FOUND("P401 - Post Not Found", "게시글을 찾을 수 없습니다.");

    private final String code;
    private final String message;

    ErrorCodeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
