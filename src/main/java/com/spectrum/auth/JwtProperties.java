package com.spectrum.auth;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotEmpty
    private final String secretKey;
    @NotNull
    private final long accessTokenExpireLength;
    @NotNull
    private final long refreshTokenExpireLength;

    public JwtProperties(String secretKey, long accessTokenExpireLength,
        long refreshTokenExpireLength) {
        this.secretKey = secretKey;
        this.accessTokenExpireLength = accessTokenExpireLength;
        this.refreshTokenExpireLength = refreshTokenExpireLength;
    }
}
