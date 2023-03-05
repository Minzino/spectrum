package com.spectrum.common.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String code;
    private String message;
    private List<String> errors = new ArrayList<>();

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

}
