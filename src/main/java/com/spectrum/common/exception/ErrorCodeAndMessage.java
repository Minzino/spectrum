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
    COMMENT_NOT_FOUND_EXCEPTION("C404 - Comment Not Found", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_PARENT_EXCEPTION("P400 - This Comment is Not Parent", "해당 댓글에는 대댓글을 작성할 수 없습니다.",
        HttpStatus.BAD_REQUEST),
    INVALID_PAGE_OR_SIZE("P400 - Invalid Page or Size", "페이지와 사이즈 값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeAndMessage(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
