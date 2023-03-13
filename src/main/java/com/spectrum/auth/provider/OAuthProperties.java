package com.spectrum.auth.provider;

import com.spectrum.auth.dto.ProviderPropertiesDto;
import com.spectrum.auth.dto.UserPropertiesDto;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "oauth2")
public class OAuthProperties {
    private final Map<String, UserPropertiesDto> user = new HashMap<>();
    private final Map<String, ProviderPropertiesDto> provider = new HashMap<>();
}
