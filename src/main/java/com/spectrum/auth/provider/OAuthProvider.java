package com.spectrum.auth.provider;

import com.spectrum.auth.dto.ProviderPropertiesDto;
import com.spectrum.auth.dto.UserPropertiesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthProvider {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public OAuthProvider(UserPropertiesDto user, ProviderPropertiesDto provider) {
        this(
            user.getClientId()
            , user.getClientSecret()
            , user.getRedirectUri()
            , provider.getTokenUri()
            , provider.getUserInfoUri());
    }

    @Builder
    public OAuthProvider(String clientId, String clientSecret, String redirectUrl, String tokenUrl, String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }
}
