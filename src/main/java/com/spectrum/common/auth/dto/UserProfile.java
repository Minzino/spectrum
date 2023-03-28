package com.spectrum.common.auth.dto;

import com.spectrum.domain.user.Authority;
import com.spectrum.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile {
    private final String oauthId;
    private final String email;
    private final String name;
    private final String imageUrl;

    @Builder
    public UserProfile(String oauthId, String email, String name, String imageUrl) {
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public User toUser() {
        return User.builder()
            .oauthId(oauthId)
            .email(email)
            .name(name)
            .imageUrl(imageUrl)
            .authority(Authority.GUEST)
            .build();
    }
}
