package com.spectrum.auth.dto;

import lombok.Getter;

@Getter
public class ProviderPropertiesDto {

    private final String tokenUri;
    private final String userInfoUri;

    public ProviderPropertiesDto(String tokenUri, String userInfoUri) {
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }
}
