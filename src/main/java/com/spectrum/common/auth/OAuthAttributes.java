package com.spectrum.common.auth;

import com.spectrum.common.auth.dto.UserProfile;
import java.util.Arrays;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthAttributes {
    KAKAO(Provider.KAKAO) {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            Provider provider = getProvider();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get(provider.getAccountKey());
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get(provider.getProfileKey());
            return UserProfile.builder()
                .oauthId(String.valueOf(attributes.get(provider.getIdKey())))
                .email((String) kakaoAccount.get(provider.getEmailKey()))
                .name((String) profile.get(provider.getNameKey()))
                .imageUrl((String) profile.get(provider.getImageUrlKey()))
                .build();
        }
    },
    GITHUB(Provider.GITHUB) {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            Provider provider = getProvider();
            return UserProfile.builder()
                .oauthId(String.valueOf(attributes.get(provider.getIdKey())))
                .email((String) attributes.get(provider.getEmailKey()))
                .name((String) attributes.get(provider.getNameKey()))
                .imageUrl((String) attributes.get(provider.getImageUrlKey()))
                .build();
        }
    },
    NAVER(Provider.NAVER) {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            Provider provider = getProvider();
            Map<String, Object> response = (Map<String, Object>) attributes.get(provider.getAccountKey());
            return UserProfile.builder()
                .oauthId((String) response.get(provider.getIdKey()))
                .email((String) response.get(provider.getEmailKey()))
                .name((String) response.get(provider.getNameKey()))
                .imageUrl((String) response.get(provider.getImageUrlKey()))
                .build();
        }
    },
    GOOGLE(Provider.GOOGLE) {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            Provider provider = getProvider();
            return UserProfile.builder()
                .oauthId(String.valueOf(attributes.get(provider.getIdKey())))
                .email((String) attributes.get(provider.getEmailKey()))
                .name((String) attributes.get(provider.getNameKey()))
                .imageUrl((String) attributes.get(provider.getImageUrlKey()))
                .build();
        }
    };

    private final Provider provider;

    public static UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
            .filter(provider -> provider.getProvider().getName().equals(providerName))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .of(attributes);
    }

    public abstract UserProfile of(Map<String, Object> attributes);
}
