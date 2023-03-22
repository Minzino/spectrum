package com.spectrum.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeAndMessage {

    SERVER_ERROR("S500 - Internal Server Error", "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("F400 - Invalid Request", "요청 정보를 확인해주세요.", HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND("P404 - Post Not Found", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_USER("U401 - Invalid User", "유효하지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("U404 - User Not Found", "유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_AUTHOR("A403 - Not Author", "작성자가 아닙니다.", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("T401 - Token Expired", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("T400 - Invalid Token", "유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
    HTTP_SERVLET_REQUEST_NULL("H400 - HttpServletRequest Null", "HttpServletRequest가 null입니다.",
        HttpStatus.BAD_REQUEST),
    COMMENT_NOT_FOUND_EXCEPTION("C404 - Comment Not Found", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeAndMessage(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
