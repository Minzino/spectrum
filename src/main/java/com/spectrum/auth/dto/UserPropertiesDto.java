package com.spectrum.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPropertiesDto {

    private String clientId;
    private String clientSecret;
    private String RedirectUri;

}
