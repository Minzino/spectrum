package com.spectrum.common.auth.dto;

import com.spectrum.domain.user.Authority;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    private String imageUrl;
    @NotNull
    private Authority authority;
    @NotNull
    private String tokenType;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;

    @Builder
    public LoginResponse(Long id, String name, String email, String imageUrl, Authority authority,
        String tokenType, String accessToken, String refreshToken) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.authority = authority;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
