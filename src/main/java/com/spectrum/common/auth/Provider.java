package com.spectrum.common.auth;

import lombok.Getter;

@Getter
public enum Provider {
    KAKAO("kakao", "id", "kakao_account", "profile", "email", "nickname", "profile_image_url"),
    GITHUB("github", "id", null, null, "email", "name", "avatar_url"),
    NAVER("naver", "id", "response", null, "email", "name", "profile_image"),
    GOOGLE("google", "sub", null, null, "email", "name", "picture");

    private final String name;
    private final String idKey;
    private final String accountKey;
    private final String profileKey;
    private final String emailKey;
    private final String nameKey;
    private final String imageUrlKey;

    Provider(String name, String idKey, String accountKey, String profileKey, String emailKey, String nameKey, String imageUrlKey) {
        this.name = name;
        this.idKey = idKey;
        this.accountKey = accountKey;
        this.profileKey = profileKey;
        this.emailKey = emailKey;
        this.nameKey = nameKey;
        this.imageUrlKey = imageUrlKey;
    }
}
