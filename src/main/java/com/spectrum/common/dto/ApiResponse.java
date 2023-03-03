package com.spectrum.common.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private static final String SUCCESS_CODE = "S001";
    private static final String SUCCESS_MESSAGE = "정상 처리";

    private final String code;
    private final String message;
    private final T data;

    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }


    public static <Void> ApiResponse<Void> success() {
        return new ApiResponse<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static <T> ApiResponse<T> error(String errorCode, String message, T data) {
        return new ApiResponse<>(errorCode, message, data);
    }

    public static <T> ApiResponse<T> error(String errorCode, String message) {
        return new ApiResponse<>(errorCode, message, null);
    }

}
