package com.spectrum.auth;

import com.spectrum.auth.dto.ProviderPropertiesDto;
import com.spectrum.auth.dto.UserPropertiesDto;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {
    private final Map<String, UserPropertiesDto> user;
    private final Map<String, ProviderPropertiesDto> provider;
}
