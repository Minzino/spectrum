package com.spectrum.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderPropertiesDto {

    private String tokenUri;
    private String userInfoUri;
}
