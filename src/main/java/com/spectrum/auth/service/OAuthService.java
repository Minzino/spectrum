package com.spectrum.auth.service;

import com.spectrum.auth.provider.InMemoryProviderRepository;
import com.spectrum.auth.provider.OAuthProvider;
import com.spectrum.auth.provider.JwtProvider;
import com.spectrum.auth.OAuthAttributes;
import com.spectrum.auth.dto.LoginResponse;
import com.spectrum.auth.dto.OAuthTokenResponse;
import com.spectrum.auth.dto.UserProfile;
import com.spectrum.domain.user.User;
import com.spectrum.repository.user.UserRepository;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final InMemoryProviderRepository inMemoryProviderRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public LoginResponse login(String providerName, String code) {
        // 프론트에서 넘어온 provider 이름을 통해 InMemoryProviderRepository에서 OAuthProvider 가져오기
        OAuthProvider provider = inMemoryProviderRepository.findByProviderName(providerName);

        // TODO access token 가져오기
        OAuthTokenResponse tokenResponse = getToken(code, provider);

        // TODO 유저 정보 가져오기
        UserProfile userProfile = getUserProfile(providerName, tokenResponse, provider);

        // TODO 유저 DB에 저장
        User user = saveOrUpdate(userProfile);

        String accessToken = jwtProvider.createAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtProvider.createRefreshToken();

        return LoginResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .imageUrl(user.getImageUrl())
            .authority(user.getAuthority())
            .tokenType("Bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    private User saveOrUpdate(UserProfile userProfile) {
        User user = userRepository.findByOauthId(userProfile.getOauthId())
            .map(entity -> entity.update(
                userProfile.getEmail(), userProfile.getName(), userProfile.getImageUrl()))
            .orElseGet(userProfile::toUser);
        return userRepository.save(user);
    }

    private OAuthTokenResponse getToken(String code, OAuthProvider provider) {
        return WebClient.create()
            .post()
            .uri(provider.getTokenUrl())
            .headers(header -> {
                header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            })
            .bodyValue(tokenRequest(code, provider))
            .retrieve()
            .bodyToMono(OAuthTokenResponse.class)
            .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code, OAuthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", provider.getClientId());
        formData.add("redirect_uri", provider.getRedirectUrl());
        formData.add("code", code);
        formData.add("client_secret", provider.getClientSecret());
        return formData;
    }

    private UserProfile getUserProfile(String providerName, OAuthTokenResponse tokenResponse,
        OAuthProvider provider) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponse);
        return OAuthAttributes.extract(providerName, userAttributes);
    }

    // OAuth 서버에서 유저 정보 map으로 가져오기
    private Map<String, Object> getUserAttributes(OAuthProvider provider,
        OAuthTokenResponse tokenResponse) {
        return WebClient.create()
            .get()
            .uri(provider.getUserInfoUrl())
            .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
            })
            .block();
    }
}
