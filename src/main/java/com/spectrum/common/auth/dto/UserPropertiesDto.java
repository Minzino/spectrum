package com.spectrum.common.auth.dto;

import lombok.Getter;

@Getter
public class UserPropertiesDto {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public UserPropertiesDto(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }
}
